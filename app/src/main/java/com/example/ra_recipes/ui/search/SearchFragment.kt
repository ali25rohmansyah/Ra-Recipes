package com.example.ra_recipes.ui.search

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import clarifai2.api.ClarifaiBuilder
import clarifai2.dto.input.ClarifaiInput
import clarifai2.dto.model.output.ClarifaiOutput
import clarifai2.dto.prediction.Concept
import com.example.ra_recipes.R
import com.example.ra_recipes.databinding.FragmentSearchBinding
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_search.*
import java.io.File
import java.io.IOException

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var status: String
    private var photoPath: String? = null
    private lateinit var dialog:AlertDialog


    private inner class ClarifaiTask : AsyncTask<File?, Int?, Boolean>() {

        override fun doInBackground(vararg images: File?): Boolean? {
            val client = ClarifaiBuilder("ce702309aab54ca8a279c2693210701c").buildSync()
            var predictionResults: List<ClarifaiOutput<Concept>>
            for (image in images) {
                predictionResults = client.defaultModels.generalModel().predict()
                    .withInputs(ClarifaiInput.forImage(image!!))
                    .executeSync()
                    .get()
                for (result in predictionResults) {
                    for (datum in result.data()) {

                        activity!!.runOnUiThread {
                            textView2.append(datum.name()+"\n")
                        }
//                        if (datum.name()!!.contains("phone")) return true
                    }
                }
            }
            return false
        }
        override fun onPostExecute(result: Boolean) {
            // Delete photo
            File(photoPath!!).delete()
            photoPath = null

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )

        //ViewModel
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        binding.searchViewModel = searchViewModel

        searchViewModel.status.observe(this, Observer { statusMsg ->
            status = statusMsg
            binding.textView2.text = statusMsg
            if (status != "processing an image") dialog.dismiss()
        })

        binding.btnImg.setOnClickListener {val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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
                val photoURI = FileProvider.getUriForFile(requireContext(),
                    "com.example.ra_recipes.fileprovider",
                    photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (photoPath != null){
            dialog = SpotsDialog.Builder()
                .setContext(requireContext())
                .setMessage(status)
                .setCancelable(false)
                .build()
            dialog.show()
            ClarifaiTask().execute(File(photoPath!!))
        }
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
    }
}