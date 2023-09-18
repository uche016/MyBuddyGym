package com.example.mybuddygym

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.mybuddygym.databinding.FragmentHomeBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val PERMISSION_REQUEST_CODE = 200
    private var mNavController: NavController? = null


    @Inject
  //  lateinit var viewModelFactory: HomeViewModel.HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavGraph()

    }

    private fun setUpNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_page_nav_host_fragment) as NavHostFragment?

        mNavController = navHostFragment?.navController

        // Getting the navController's navigation inflater
        val navInflater: NavInflater? = mNavController?.navInflater

        // Inflating navController's graph using its navigation inflater to inflate
        // the navigation resource file containing fragment navigation flow
//        navInflater?.let {
//            mNavController?.graph = it.inflate(R.navigation.checker_collection_nav_graph)
//        }
        val navGraph = navInflater!!.inflate(R.navigation.my_buddy_app_navigation)
        navGraph.startDestination = R.id.homeFragment
        mNavController?.setGraph(navGraph, null)

        // Choose the fragment the navigation will show first.

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // TODO add other permission requests
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }


}