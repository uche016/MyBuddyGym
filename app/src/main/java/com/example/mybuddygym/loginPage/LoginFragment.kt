package com.example.mybuddygym.loginPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mybuddygym.databinding.FragmentLogInPageBinding
import com.example.mybuddygym.utils.SharedPreferenceMyBuddyApp

class LoginFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var mBinding: FragmentLogInPageBinding
    private lateinit var mSharedPreference: SharedPreferenceMyBuddyApp


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentLogInPageBinding.inflate(inflater)
        return mBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBinding()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setBinding() {
        mBinding.btnLogin.setOnClickListener {
        }

    }
}