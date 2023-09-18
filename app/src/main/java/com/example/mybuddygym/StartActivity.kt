package com.example.mybuddygym

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.mybuddygym.utils.AppUser
import com.example.mybuddygym.utils.Constants
import com.example.mybuddygym.utils.SharedPreferenceMyBuddyApp
import java.util.Objects

class StartActivity : AppCompatActivity() {
    private lateinit var binding: FragmentLogInPageBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var mAppUser: AppUser? = null
    private lateinit var sharedPreferenceMyBuddyApp: SharedPreferenceMyBuddyApp
    private val storagePermissionRequestCode = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            FragmentLogInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCompulsoryVariables()
        setBinding()
        setUpNavGraph()

    }

    private fun setUpNavGraph() {
        val toolbar = binding.assetManagerToolbar.toolbar
        setSupportActionBar(toolbar)


        //Navigation Up Button
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initCompulsoryVariables() {
        sharedPreferenceMyBuddyApp = SharedPreferenceMyBuddyApp(this)
        mAppUser =
            sharedPreferenceMyBuddyApp.getValue(AppUser::class.java, Constants.KEY_CURRENT_APP_USER)
    }

    private fun setBinding() {
//        binding.appUser = mAppUser
//        binding..back_tap.setOnClickListener {
            onBackPressed()
        }
//        (binding..img_hamburger_menu as? View)?.setOnClickListener {
//            val popup = PopupMenu(this, binding.assetManagerToolbar.img_hamburger_menu)
//            val inflater = popup.menuInflater
////            inflater.inflate(R.menu.menu, popup.menu)
//
//            popup.setOnMenuItemClickListener(
//                PopupMenu.OnMenuItemClickListener { item ->
//                    when (item.itemId) {
//
////                        R.id.log_out -> {
////                            sharedPreferenceMyBuddyApp.putValue(
////                                Constants.KEY_IS_LOGGED_IN,
////                                false
////                            )
//////                            val intent = Intent(this, LoginActivity::class.java)
////                            startActivity(intent)
////                            finish()
////                        }
//                    }
//                    true
//                }
//            )
//            popup.show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


}