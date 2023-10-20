package com.example.mybuddygym.loginPage

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mybuddygym.databinding.DialogLoadingBinding

class LoadingDialog(context: Context, message: String? = null) {

    private val dialog: AlertDialog
    private val activity = context as AppCompatActivity
    private val binding: DialogLoadingBinding

    init {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        binding = DialogLoadingBinding.inflate(inflater)
        if (!message.isNullOrEmpty()) {
            binding.loadingMessage.text = message
        }
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()
    }

    fun startDialog() {
        dialog.show()
    }

    fun endDialog() {
        dialog.dismiss()
    }
}
