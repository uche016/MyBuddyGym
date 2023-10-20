package com.example.mybuddygym.signUp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SIgnUpViewModel  (
    private val application: Application
) : ViewModel() {



    class SIgnUpViewModelFactory @Inject constructor(
        private val application: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SIgnUpViewModel(application) as T
        }
    }
}