package com.example.ra_recipes.view.ui.search

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept
import com.example.ra_recipes.R
import com.example.ra_recipes.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import java.io.File
import java.io.IOException

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private var photoPath: String? = null
    private lateinit var dialog:AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )

        //ViewModel
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        //set databinding with viewModel
        binding.searchViewModel = searchViewModel

        binding.btnImg.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
                val photoFile: File
                try {
                    val storageDir = activity!!.filesDir
                    photoFile = File.createTempFile(
                        "SNAPSHOT",
                        ".jpg",
                        storageDir
                    )
                    photoPath = photoFile.absolutePath
                }catch (ex: IOException){
                    return@setOnClickListener
                }

                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.ra_recipes.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }

        searchViewModel.status.observe(this, Observer {
            // Delete photo
            if (it == "done"){
                File(photoPath!!).delete()
                photoPath = null
                val allLabel = searchViewModel.label.value?.joinToString(separator = ",")
                view!!.findNavController().navigate(
                    SearchFragmentDirections.actionNavigationHomeToRecipesFilterFragment(
                        allLabel!!
                    )
                )
                dialog.dismiss()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (photoPath != null){
            dialog = SpotsDialog.Builder()
                .setContext(requireContext())
                .setMessage(searchViewModel.status.value)
                .setCancelable(false)
                .build()
            dialog.show()
            searchViewModel.predictImage(File(photoPath!!))
        }
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
    }
}