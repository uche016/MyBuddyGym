package com.example.mybuddygym.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.mybuddygym.R

class Utils private constructor() {
    companion object {
        private val TAG: String = Utils::class.java.simpleName

        /**
         * @param context: The context
         * @param title: Dialog title
         * @param message: The dialog message
         * @param buttonNames: List containing the 2 button names
         * @param buttonActions: List containing the 2 button actions
         * @param isCancelable: boolean indicating if touching outside the dialog will close it
         */

        fun showActionDialog(
            context: Context,
            title: String,
            message: String,
            buttonNames: List<String>,
            buttonActions: List<() -> Unit>,
            isCancelable: Boolean = false,
        ) {
            val dialog = Dialog(
                context,
                com.google.android.material.R.style.Theme_AppCompat_Light_Dialog_Alert
            )
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_asset_manager_two_options)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val titleTextView = dialog.findViewById<TextView>(R.id.text_view_title)
            titleTextView.text = title

            val messageTextView = dialog.findViewById<TextView>(R.id.text_view_message)
            messageTextView.text = message

            val button1 = dialog.findViewById<Button>(R.id.button_1)
            val button2 = dialog.findViewById<Button>(R.id.button_2)

            button1.text = buttonNames[0]
            button1.setOnClickListener {
                dialog.dismiss()
                buttonActions[0]()
            }

            button2.text = buttonNames[1]
            button2.setOnClickListener {
                dialog.dismiss()
                buttonActions[1]()
            }

            dialog.show()
        }


        fun showSingleActionDialog(
            context: Context,
            message: String,
            buttonAction: List<() -> Unit>,
            isCancelable: Boolean = false,
            callback: () -> Unit = {},
        ) {
            val dialog = Dialog(
                context,
                com.google.android.material.R.style.Theme_AppCompat_Light_Dialog_Alert
            )
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_id_success)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textview = dialog.findViewById<TextView>(R.id.tv_success_message)
            textview.text = message

            val okBtn = dialog.findViewById<Button>(R.id.btn_next)
            okBtn.setOnClickListener {
                buttonAction[0]()
                dialog.dismiss()
                callback()
            }

            dialog.show()
        }

        fun singleActionDialog(
            context: Context,
            message: String,
            buttonAction: List<() -> Unit>,
            isCancelable: Boolean = false,
            callback: () -> Unit = {},
        ) {
            val dialog = Dialog(
                context,
                com.google.android.material.R.style.Theme_AppCompat_Light_Dialog_Alert
            )
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_single_action)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textview = dialog.findViewById<TextView>(R.id.tv_success_message)
            textview.text = message

            val okBtn = dialog.findViewById<Button>(R.id.btn_ok)
            okBtn.setOnClickListener {
                buttonAction[0]()
                dialog.dismiss()
                callback()
            }

            dialog.show()
        }


    }
}

