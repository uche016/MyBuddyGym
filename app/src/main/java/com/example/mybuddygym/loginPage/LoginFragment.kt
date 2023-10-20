package com.example.mybuddygym.loginPage

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkManager
import com.example.mybuddygym.database.model.user.User
import com.example.mybuddygym.databinding.FragmentLogInPageBinding
import com.example.mybuddygym.utils.Constants
import com.example.mybuddygym.utils.SharedPreferenceMyBuddyApp

class LoginFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var mBinding: FragmentLogInPageBinding
    private lateinit var mSharedPreference: SharedPreferenceMyBuddyApp
    private val viewModel by viewModels<LoginViewModel>()
    private val workManager by lazy { WorkManager.getInstance(requireContext()) }
    private val dialog by lazy { LoadingDialog(requireContext(), "Fetching User data...") }
    private var dbUsername: List<String>? = null
    private var rodent: ArrayList<String>? = null
    private lateinit var user: User
    private val userNameKey = "username"
    private val passwordKey = "password"
    private val loginMap = mutableMapOf<String, String?>(userNameKey to null, passwordKey to null)
    var name: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentLogInPageBinding.inflate(inflater)
        return mBinding.root
    }

    private fun initCompulsoryVariables (){
        // This variable checks if the person has logged in, temporary save it and this is used fro further actions on the application
        val isLoggedIn = mSharedPreference.getValue(
            Boolean::class.java,
            Constants.KEY_IS_LOGGED_IN
        )


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBinding()
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun isValidLogin(username: String, password: String): Boolean {
//        val mUser = viewModel.getUserFromDB(username)
//        Timber.i("User", mUser)
//
//        return if (mUser != null) {
//            user = mUser
//            val validLogin = isValidPass(clearTextPassword = password, hashedPass = user.password!!)
//            if (!validLogin) {
//                mBinding.passwordLayout.error = getString(R.string.login_incorrect_password)
//            }
//            validLogin
//        } else {
//            mBinding.usernameLayout.error = getString(R.string.login_invalid_username)
//            false
//        }
//        return true
//    }

    private fun setBinding() {
        mBinding.btnLogin.setOnClickListener {

            mBinding.btnLogin.setOnClickListener {
//                if (isValidLogin(
//                        username = loginMap[userNameKey].toString(),
//                        password = loginMap[passwordKey].toString()
//                    )
//                ) {
//                    mSharedPreference.putValue(
//                        Constants.KEY_IS_LOGGED_IN,
//                        true
//                    )
//
//                    mSharedPreference.putValue(
//                        Constants.KEY_CURRENT_APP_USER,
//
//                        AppUser(
//                            userId = user.id,
//                            firstName = user.firstName!!,
//                            lastName = user.lastName!!,
//                            accessLevel = user.accessLevel!!,
//                            password = user.password!!,
//                            hubId = "",
//                            appVersionNo = "",
//                        )
//                    )
//
//                    requireActivity().run {
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
//                    }
//                }
            }

        }
    }

    private fun getStaffDetails() {
        dbUsername = viewModel.getUserFromDB()

        rodent = ArrayList()
        for (i in (dbUsername)?.indices!!) {
            var x = (dbUsername)?.get(i)
            // Log.d("KKEEYY", String.valueOf(x));
            if (x != null) {
                x = x.replace(" ".toRegex(), "_")
            }
            //  Log.d("KKEEYY", String.valueOf(x));
            if (x != null) {
                rodent!!.add(x)
            }
        }
        Log.d("KKEEYY", rodent.toString())

        val name: ArrayAdapter<*> =
            ArrayAdapter(
                requireContext(), R.layout.simple_list_item_1,
                dbUsername!!
            )

//        mBinding..setAdapter(name)
//        mBinding.usernameEditText.threshold = 1





    }
}