package com.example.mybuddygym.selectworkouttype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mybuddygym.databinding.SelectExcerciseTypeFragmentBinding
import com.example.mybuddygym.utils.SharedPreferenceMyBuddyApp

class SelectWorkoutTypeFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var mBinding: SelectExcerciseTypeFragmentBinding
    private lateinit var mSharedPreference: SharedPreferenceMyBuddyApp  //fetching and getting data


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = SelectExcerciseTypeFragmentBinding.inflate(inflater)
        return mBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBinding()
        super.onViewCreated(view, savedInstanceState)
    }
//assigns what the buttons would do so all the interactions
    private fun setBinding() {


    }
}