package com.example.mybuddygym.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mybuddygym.BaseApplication
import com.example.mybuddygym.databinding.FragmentCreateAnAccountBinding
import com.example.mybuddygym.utils.SharedPreferenceMyBuddyApp
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SIgnUpViewModel
    private lateinit var binding: FragmentCreateAnAccountBinding
    private lateinit var sharedPreferenceMyBuddyApp: SharedPreferenceMyBuddyApp

    @Inject
    lateinit var viewModelFactory: SIgnUpViewModel.SIgnUpViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding =  FragmentCreateAnAccountBinding.inflate(inflater)


        initCompulsoryVariables()
        setBinding()
        return binding.root
    }

    private fun initCompulsoryVariables() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get()


        sharedPreferenceMyBuddyApp = SharedPreferenceMyBuddyApp(requireContext())

    }

    private fun setBinding() {

        binding.createAnAccount.setOnClickListener {


        }

        binding.passwordLogin.setOnClickListener {
          //  findNavController().navigate(R.id.action_createAnAccountFragment_to_launchScreenLoginFragment)
        }
    }


    private fun addDataIntoDB() {

    }

}

