package com.example.ra_recipes.ui.search

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ra_recipes.R
import com.example.ra_recipes.adapter.SelectedImageAdapter
import com.example.ra_recipes.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val PERMISSION_CODE: Int = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var viewAdapter: SelectedImageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: SearchViewModel
//    private val image = mutableListOf<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        viewModel = ViewModelProviders.of(this).get(searchViewModel::class.java)
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = SelectedImageAdapter(viewModel)

        binding.selectedRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
        binding.btnImg.setOnClickListener {
            //check permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){

                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }else{
                    //Open Camera
                    openCamera()
                }
            }
        }
        binding.btnDone.setOnClickListener {
            if (viewModel.image.value!!.size <= 0) {
                Toast.makeText(activity, "Select at least one image", Toast.LENGTH_SHORT).show()
            } else{
                findNavController().navigate(R.id.action_navigation_home_to_recipesFilterFragment)
            }
        }
        return binding.root
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    openCamera()
                }else{
                    Toast.makeText(activity, "permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
            val imageBitmap = data.extras?.get("data") as Bitmap
            viewModel.image.value!!.add(imageBitmap)
        }
    }
}