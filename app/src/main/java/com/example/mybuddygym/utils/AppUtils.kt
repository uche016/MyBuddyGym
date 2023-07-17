package com.example.mybuddygym.utils

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.math.*

class AppUtils private constructor() {

    companion object {
        private val TAG: String = AppUtils::class.java.simpleName

        /**
         * Check if the device is connected to the internet.
         *
         * @param context activity that calls this method.
         * @return true if there is a network connection else false.
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: Network? = cm.activeNetwork
            return activeNetwork != null
        }

        /**
         * Check if there is an active Internet connection by trying to connect
         * to a known server via http.
         *
         * PS: It is not advisable to run this method on the Main Thread.
         *
         * @param context activity that calls this method.
         * @return true if there is a network connection else false.
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun deviceHasActiveInternetConnection(context: Context): Boolean {
            if (isNetworkAvailable(context)) {
                // Check if there is an active Internet connection by trying to connect
                // to a known server via http.
                try {
                    val urlConnection: HttpURLConnection =
                        URL("http://clients3.google.com/generate_204").openConnection() as HttpURLConnection
                    urlConnection.setRequestProperty("Staff-Agent", "Android")
                    urlConnection.setRequestProperty("Connection", "close")
                    urlConnection.connectTimeout = 1500
                    urlConnection.connect()
                    return (urlConnection.responseCode == 204 && urlConnection.contentLength == 0)
                } catch (e: IOException) {
                    Log.e(TAG, "Error checking internet connection", e)
                }
            } else {
                Log.i(TAG, "No internet connection!!!")
            }
            return false
        }


        /**
         * Function to get the AppUser object
         * */
        fun getAppUser(context: Context): AppUser? {
            val sharedPreferenceMyBuddyAppGeneral = SharedPreferenceMyBuddyApp(context)
            return sharedPreferenceMyBuddyAppGeneral.getValue(
                AppUser::class.java,
                Constants.KEY_CURRENT_APP_USER
            )
        }


        /**
         *
         * @param context activity that calls this method.
         * @param message message you want to display to the user.
         * @param toastLength either Toast.SHORT or Toast.LONG.
         * @param prevToast if you want to cancel an existing previous toast before displaying a new
         *                  one.
         * @return the new toast object.
         */
        fun showToast(
            context: Context,
            message: String,
            toastLength: Int,
            prevToast: Toast?,
        ): Toast {
            // cancel the previous prevToast message before setting a new one
            prevToast?.cancel()
            val newToast = Toast.makeText(context, message, toastLength)

            // Center align text in prevToast.
//            val view = newToast.view!!.findViewById<TextView>(R.id.message)
//            if (view != null) view.gravity = Gravity.CENTER

            // Set the prevToast view at the center of the device.
            newToast.setGravity(Gravity.CENTER, 0, 0)
            newToast.show()
            return newToast
        }

        /**
         * Get Date string in required format
         * Returns today's date string if no date is passed
         * @param keyDateFormat: options can be found in constants class
         */
        fun getDateString(
            keyDateFormat: String,
            date: Date = Date(),
            timeZone: String? = null,
        ): String {
            val dateFormat: SimpleDateFormat = when (keyDateFormat) {
                Constants.KEY_DATE_FORMAT_CONCAT -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_CONCAT, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_CONCAT2 -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_CONCAT2, Locale.getDefault())
                }
                Constants.KEY_TIME_FORMAT_DEFAULT -> {
                    SimpleDateFormat(Constants.TIME_FORMAT_DEFAULT, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_SPREAD -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_SPREAD, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_SLASHED_DMY -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_SLASHED_DMY, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_UI_FULL -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_UI_FULL, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_UI_MEDIUM -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_UI_MEDIUM, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_SYSTEM -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_SYSTEM, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_DEFAULT_FILE -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT_FILE, Locale.getDefault())
                }
                Constants.KEY_DATE_FORMAT_HYPHEN_DMY -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_HYPHEN_DMY, Locale.getDefault())
                }
                else -> {
                    SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, Locale.getDefault())
                }
            }

            if (!timeZone.isNullOrEmpty()) {
                dateFormat.timeZone = TimeZone.getTimeZone(timeZone)
            }

            return dateFormat.format(date)
        }

        /**
         * Parses any valid date string
         * Returns null if string is invalid
         * @param dateString: the date String to be converted to date
         */
        fun parseAnyDateString(dateString: String): Date? {
            val dateFormats = arrayOf(
                "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
                "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
                "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
                "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
                "yyyy:MM:dd HH:mm:ss", "yyyy-MM-dd", "dd/MM/yyyy",
                "EEEE, MMM d, yyyy", "d MMMM yyyy", "yyyyMMdd", "yyyy/MM/dd"
            )

            for (dateFormat in dateFormats) {
                val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
                try {
                    return sdf.parse(dateString)
                } catch (e: ParseException) {
                }
            }

            return null
        }

        /**
         * Transforms dateString to target date string format
         * @param dateString: the date string to be transformed
         * @param targetFormatKey: string key that references the target date format. Please see Constants.kt for acceptable keys
         */
        @Throws(ParseException::class)
        fun transFormDateString(dateString: String, targetFormatKey: String): String {
            val date =
                parseAnyDateString(dateString) ?: throw ParseException("Invalid date string", 11)
            return getDateString(targetFormatKey, date)
        }

        /**
         * present a success dialog when refreshment details have been successfully set
         * @param context the context
         * @param message: message to be displayed
         * @param isCancelable: boolean value indicating if dialog isCancelable
         * @param callback: the method to be called when the ok button is called
         */
        /*
        fun showSuccessDialog(
            context: Context,
            message: String,
            isCancelable: Boolean = false,
            callback: () -> Unit = {},
        ) {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_hr_members_event_location_success_custom)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textview = dialog.findViewById<TextView>(R.id.textView_event_location_succeccfully)
            textview.text = message

            val okBtn = dialog.findViewById<Button>(R.id.ok)

            okBtn.setOnClickListener {
                dialog.dismiss()
                callback()
            }

            dialog.show()
        }

         */

        /**
         * present a success dialog when refreshment details have been successfully set
         * @param context the context
         * @param message: message to be displayed
         * @param isCancelable: boolean value indicating if dialog isCancelable
         * @param spannable: A spannable message to use in place of message
         * @param btnText: The text of the dialog button
         * @param callback: the method to be called when the ok button is called
         */

        /*
        fun showSuccessDialog(
            context: Context,
            message: String,
            isCancelable: Boolean = false,
            btnText: String? = null,
            spannable: SpannableString? = null,
            callback: () -> Unit = {},
        ) {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_hr_members_event_location_success_custom)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textview = dialog.findViewById<TextView>(R.id.textView_event_location_succeccfully)
            textview.text = message

            val okBtn = dialog.findViewById<Button>(R.id.ok)

            // Set optional params
            if (btnText != null) {
                okBtn.text = btnText
            }

            if (spannable != null) {
                textview.setText(spannable, TextView.BufferType.SPANNABLE)
            }

            okBtn.setOnClickListener {
                dialog.dismiss()
                callback()
            }

            dialog.show()
        }

         */

        /**
         * @param context: The context
         * @param title: Dialog title
         * @param message: The dialog message
         * @param buttonNames: List containing the 2 button names
         * @param buttonActions: List containing the 2 button actions
         * @param isCancelable: boolean indicating if touching outside the dialog will close it
         */

        /*
        fun showActionSuccessDialog(
            context: Context,
            title: String,
            message: String,
            buttonNames: List<String>,
            buttonActions: List<() -> Unit>,
            isCancelable: Boolean = false,
        ) {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_custom_action_success)
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

         */

        /**
         * @param context: The context
         * @param title: Dialog title
         * @param message: The dialog message
         * @param isCancelable: boolean indicating if touching outside the dialog will close it
         * @return dialog instance for further manipulation
         */

        /*
        fun showNoActionDialog(
            context: Context,
            title: String,
            message: String,
            isCancelable: Boolean = true,
        ): Dialog {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_id_alert_no_button)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val titleTextView = dialog.findViewById<TextView>(R.id.text_view_title)
            titleTextView.text = title

            val messageTextView = dialog.findViewById<TextView>(R.id.text_view_message)
            messageTextView.text = message
            dialog.show()
            return dialog
        }

         */

        /**
         * Creates a file directory and a .nomedia file if it does not exist in external files dir
         */

        fun createMediaDirectory(applicationContext: Context, directory: String, root: String) {
            val imageDirectory =
                File(
                    Objects.requireNonNull<File>(
                        applicationContext.getExternalFilesDir(
                            root
                        )
                    ).absoluteFile,
                    directory
                )
            Log.d(TAG, "Directory is ${imageDirectory.absolutePath}")
            imageDirectory.mkdirs()
            val noMediaFile = File(imageDirectory.absoluteFile, Constants.NO_MEDIA_FILE)

            if (!noMediaFile.exists()) {
                val outNoMedia = FileOutputStream(noMediaFile)
                outNoMedia.flush()
                outNoMedia.close()
            }
        }

        /**
         * Get tomorrow or any date from the current Date by specifing count of date from current date i.e.whenDateFromCurrent
         * Returns today's date string
         * @param keyDateFormat: options can be found in constants class
         * whenDateFromCurrent: count of required date from current date i.e. if we want tomorrow date then its value would be 1
         */
        fun getRequiredDateString(
            keyDateFormat: String,
            whenDateFromCurrent: Int,
        ): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, whenDateFromCurrent)
            return SimpleDateFormat(keyDateFormat, Locale.getDefault()).format(calendar.time)
        }

        /**
         * Get tomorrow or any date from the given Date by specifing count of date from given date i.e.whenDateFromCurrent
         * Returns today's date string
         * @param keyDateFormat: options can be found in constants class
         * whenDateFromCurrent: count of required date from current date i.e. if we want tomorrow date then its value would be 1
         */
        fun getRequiredDateStringFromSpecificDate(
            keyDateFormat: String,
            dateString: String,
            whenDateFromCurrent: Int,
        ): String {
            val date = SimpleDateFormat(keyDateFormat).parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_YEAR, whenDateFromCurrent)
            return SimpleDateFormat(keyDateFormat, Locale.getDefault()).format(calendar.time)
        }

        /**
         * Get a diff between two dates
         *
         * @param oldDate the old date
         * @param newDate the new date
         * @return the diff value, in the days
         */
        fun getDateDiff(format: String, date1: String?, date2: String?): Long {

            val dateformat = SimpleDateFormat(format, Locale.getDefault())
            return TimeUnit.DAYS.convert(
                dateformat.parse(date1).time - dateformat.parse(date2).time,
                TimeUnit.MILLISECONDS
            )
        }

        /**
         * Checks if a date is past current date
         *
         * @param dateString the date we want to check for
         * @param keyDateFormat: options can be found in constants class
         * @return true if the date is past current day, false otherwise
         */
        fun isDayBeforeToday(
            keyDateFormat: String,
            dateString: String?,
        ): Boolean {
            val today = Date()
            val otherDay = SimpleDateFormat(keyDateFormat).parse(dateString)

            return otherDay.before(today)
        }

        /**
         * Display date picker
         * Note: Min date for the startDate edit text is the current date
         * @param editText: The EditText where resulting processed date is put
         * @param case: Integer value that informs the calculation of the date picker's min date
         */
        fun displayDatePicker(
            editText: EditText,
            context: Context,
            minDate: Long? = null,
        ) {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]

            // date picker dialog
            val picker = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    // selected month usually starts from 0
                    var mm = (monthOfYear + 1).toString()

                    if (mm.length == 1) {
                        mm = "0$mm"
                    }
                    var dd = dayOfMonth.toString()
                    if (dd.length == 1) {
                        dd = "0$dd"
                    }
                    editText.setText("$dd/$mm/$year")
                }, year, month, day
            )
            if (minDate != null) {
                picker.datePicker.minDate = minDate
            }
            picker.show()
        }

        fun displayDatePickerWithPastDates(editText: EditText, context: Context) {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]

            // date picker dialog
            var picker = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    // selected month usually starts from 0
                    var mm = (monthOfYear + 1).toString()

                    if (mm.length == 1) {
                        mm = "0$mm"
                    }
                    var dd = dayOfMonth.toString()
                    if (dd.length == 1) {
                        dd = "0$dd"
                    }
                    editText.setText("$dd/$mm/$year")
                }, year, month, day
            )
            picker.show()
        }

        /**
         * Extension function to capitalize the first letter of each word in a string
         */
        fun String.toTitleCase(): String {
            return this.trim().lowercase().split(" ").joinToString(" ") { str ->
                str.replaceFirstChar { firstLetter ->
                    if (firstLetter.isLowerCase()) firstLetter.titlecase(Locale.getDefault()) else firstLetter.toString()
                }
            }
        }

        /**
         * creates an alert dialog to user with the action provided in callback
         * @param context the context
         * @param message: message to be displayed
         * @param isCancelable: boolean value indicating if dialog isCancelable
         * @param callback: the method to be called when the ok button is called
         * @return Dialog object
         */

      /*  fun createSingleAlertDialog(
            context: Context,
            message: String,
            isCancelable: Boolean = false,
            callback: () -> Unit = {},
        ): Dialog {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            val inflater = (context as AppCompatActivity).layoutInflater
            val binding = DialogAlertSingleActionBinding.inflate(inflater)
            val textview = binding.textViewDialogMessage
            val dialogButton = binding.dialogButton
            val textAppearance = R.style.AgricOsSecondaryText

            textview.text = message
            textview.setTextAppearance(textAppearance)
            dialogButton.setOnClickListener {
                dialog.dismiss()
                callback()
            }

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(binding.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
       */
        /**
         * This function checks the current app version against the one expected for the module and
         * show a non-cancelable alert message if the module is completely outdated or a cancelable
         * one if it is within the threshold
         *
         * @param fragment: The fragment the check is called from
         * @param context: the context
         * @param module: The module being accessed
         * @param warningMessage: Message to be displayed to the user
         * @return Boolean value on whether the app is completely outdated or not
         */
        /*
        suspend fun checkModuleAppVersion(
            fragment: Fragment,
            context: Context,
            module: SyncEnums.Modules,
            warningMessage: String,

            ): Boolean {
            return fragment.lifecycleScope.async {
                var moduleVersionKey = ""
                var moduleReleaseDateKey = ""
                var moduleVersionThreshold = ""
                var appVersionState = false

                }

                val appConfigRepo = UniqueIdentifierRepository(context)
                val appVersion = BuildConfig.VERSION_NAME
                val remoteAppVersion =
                    withContext(Dispatchers.IO) { appConfigRepo.getAppConfigByKey(moduleVersionKey) }?.value
                if (!remoteAppVersion.isNullOrBlank()) {
                    if (!isValidAppVersion(appVersion, remoteAppVersion)) {
                        val sdfFormat = "dd-MM-yyyy"
                        val sdf = SimpleDateFormat(sdfFormat, Locale.getDefault())
                        val curDate = Date().time
                        val appReleaseDateStr =
                            withContext(Dispatchers.IO) {
                                appConfigRepo.getAppConfigByKey(moduleReleaseDateKey)
                            }?.value
                        val dateLong =
                            if (appReleaseDateStr.isNullOrBlank()) 0L else sdf.parse(
                                appReleaseDateStr
                            )?.time
                        val dateDiff =
                            if (dateLong != null) ((curDate - dateLong) / 86400000L).toInt() else 0
                        val updateThresholdStr =
                            withContext(Dispatchers.IO) {
                                appConfigRepo.getAppConfigByKey(moduleVersionThreshold)
                            }?.value
                        val updateThreshold =
                            if (updateThresholdStr.isNullOrBlank()) 0 else updateThresholdStr.toInt()
                        val updateDiff = updateThreshold - dateDiff
                        val alertHdrMsg = fragment.getString(R.string.alert)

                        when {
                            (updateDiff > 0) -> {
                                val dayMsg =
                                    if (updateDiff > 1) fragment.getString(R.string.days) else fragment.getString(
                                        R.string.day
                                    )
                                val updateThresholdMsgEnd =
                                    fragment.getString(
                                        R.string.module_outdated_msg_end,
                                        updateDiff.toString(),
                                        dayMsg
                                    )
                                val updateThresholdMsg = "$warningMessage $updateThresholdMsgEnd"
                                val dialog = showNoActionDialog(
                                    context = context,
                                    title = alertHdrMsg,
                                    message = updateThresholdMsg,
                                    false
                                )
                                delay(3000L)
                                dialog.dismiss()
                                appVersionState = true
                            }

                            (updateDiff == 0) -> {
                                val updateTodayMsgEnd =
                                    fragment.getString(R.string.today_app_ver_check)
                                val updateTodayMsg = "$warningMessage $updateTodayMsgEnd"
                                val dialog = showNoActionDialog(
                                    context = context,
                                    title = alertHdrMsg,
                                    message = updateTodayMsg,
                                    false
                                )
                                delay(3000L)
                                dialog.dismiss()
                                appVersionState = true
                            }
                            (updateDiff < 0) -> {
                                fragment.activity?.runOnUiThread {
                                    showNoActionDialog(
                                        context = context,
                                        title = alertHdrMsg,
                                        message = warningMessage,
                                        false
                                    )
                                }
                            }
                        }
                    } else {
                        appVersionState = true
                    }
                } else {
                    appVersionState = true
                }
                return@async appVersionState
            }.await()
        }

         */

        fun isValidAppVersion(appVersion: String, remoteAppVersion: String): Boolean {
            var isValidVersion = false
            val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
            val appVersionList =
                appVersion.dropLastWhile { !it.toString().matches(regex) }.split('.')
                    .map { it.toInt() }
            val remoteAppVersionList =
                remoteAppVersion.dropLastWhile { !it.toString().matches(regex) }.split('.')
                    .map { it.toInt() }
            if (appVersionList.size >= 3) {
                if (appVersionList[0] >= remoteAppVersionList[0]) {
                    isValidVersion = true
                    if ((appVersionList[1] >= remoteAppVersionList[1]) && (appVersionList[0] == remoteAppVersionList[0])) {
                        isValidVersion = true
                        if ((appVersionList[1] == remoteAppVersionList[1]) && (appVersionList[2] < remoteAppVersionList[2])) {
                            isValidVersion = false
                        }
                    } else if ((appVersionList[1] < remoteAppVersionList[1]) && (appVersionList[0] == remoteAppVersionList[0])) {
                        isValidVersion = false
                    }
                }
            }
            return isValidVersion
        }

        /**
         * @param context: the context
         * @param module: The module being accessed
         * @param action: The action being performed by the user. Eg: "Staff selected 5Ha as ID pickup size for IK0000049"
         * @param subjectID: The ID of the member the action is being performed on/for
         * @param activity: The activity or flow the user is currently on. Eg:  Log RF
         * @param misc: Any additional information that can be added
         */

//        fun generateAppUserLog(
//            context: Context,
//            misc: String = "",
//            subjectID: String = "",
//            action: String,
//            module: SyncEnums.Modules,
//            activity: String,
//        ) {
//            CoroutineScope(Dispatchers.IO).launch {
//                val staffID = SharedPreferenceGeneral(context).getValue(
//                    AppUser::class.java,
//                    Constants.KEY_CURRENT_APP_USER
//                )?.getUserId().toString()
//                val appUserLogRepo = AppUserLogRepo(context)
//                val id = IdGenerator.getInstance(context)
//                    .getId(DatabaseConstantsSync.AppUserLog.TABLE_NAME)
//                val date by lazy { getDateString(Constants.KEY_DATE_FORMAT_SPREAD) }
//                val appUserLog = AppUserLog(
//                    id = id,
//                    staffID = staffID,
//                    date = date,
//                    module = module,
//                    activity = activity,
//                    action = action,
//                    subject = subjectID,
//                    misc = misc,
//                )
//                appUserLogRepo.insertAppUserLog(appUserLog)
//            }
//        }


        /*Get Device IMEI Number*/
        fun getDeviceImeiNumber(context: Context): String {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    telephonyManager.getImei(0)
                } else {
                    telephonyManager.getDeviceId(0)
                }
            } catch (e: SecurityException) {
                Settings.Secure.getString(
                    context?.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }



        /**
         * Change current date formate to another new Date format
         */
        fun changeDateFormat(dateString: String, oldFormat: String, newFormat: String): String {
            val convertedDate = SimpleDateFormat(newFormat)
                .format(
                    SimpleDateFormat(oldFormat)
                        .parse(dateString)
                )
            return convertedDate
        }

        /**
         * Converts a list of object to String
         */
        fun <T> convertListObjToString(listObj: List<T>?): String? {
            // convert object list to string json for
            return Gson().toJson(listObj, object : TypeToken<List<T>?>() {}.type)
        }

        /**
         * Sets up Location based Time  check to confirm if user device time is the
         * same as Location time with a margin of error of one hour. it is to be set up in activities
         * that require time sensitive data. it creates a non-cancelable dialog that directs the user
         * to change device time. It also creates a non-cancelable dialog that directs
         * user to turn on device location settings if it is off
         *
         * @param activity: The activity that requires the time check
         * @param locationPermissionRequestCode: location permission request code
         */
        /*
        fun setupLocationTimeCheck(
            activity: AppCompatActivity,
            locationPermissionRequestCode: Int,
        ) {
            // nested function to check if user location is turned on and display dialog to turn on
            fun isLocationEnabled(
                locationManager: LocationManager,
                disabledLocationDialog: Dialog,
            ): Boolean {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val isLocationEnabled = locationManager.isLocationEnabled
                    if (!isLocationEnabled) {
                        disabledLocationDialog.show()
                    }
                    isLocationEnabled
                } else {
                    val locationSettingsState = Settings.Secure.getInt(
                        activity.contentResolver,
                        Settings.Secure.LOCATION_MODE,
                        Settings.Secure.LOCATION_MODE_OFF
                    )
                    val isLocationOn = (locationSettingsState != Settings.Secure.LOCATION_MODE_OFF)
                    if (!isLocationOn) {
                        disabledLocationDialog.show()
                    }
                    isLocationOn
                }
            }

            var locationManager: LocationManager? = null
            var locationProvider: String = LocationManager.GPS_PROVIDER
            val wrongTimeDialog = createSingleAlertDialog(
                activity,
                activity.getString(R.string.wrong_system_time_msg)
            ) {
                val clockSettingIntent = Intent(Settings.ACTION_DATE_SETTINGS)
                activity.startActivity(clockSettingIntent)
            }
            val disabledLocationDialog = createSingleAlertDialog(
                activity,
                activity.getString(R.string.disabled_location_msg)

            ) {
                val locationSettingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(locationSettingsIntent)
            }
            val currentLocationLive: MutableLiveData<Location?> = MutableLiveData(null)
            val locationListener =
                LocationListenerCompat { location -> currentLocationLive.value = location }

            // Setup Location Manager
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager = ContextCompat.getSystemService(
                    activity,
                    LocationManager::class.java
                ) as LocationManager

                if (isLocationEnabled(locationManager, disabledLocationDialog)) {
                    val isGPSEnabled = locationManager.isProviderEnabled(locationProvider)
                    if (isGPSEnabled) {
                        locationManager.requestLocationUpdates(
                            locationProvider,
                            1000L,
                            0f,
                            locationListener
                        )
                    } else {
                        val criteria = Criteria()
                        criteria.accuracy = Criteria.ACCURACY_FINE
                        criteria.bearingAccuracy = Criteria.ACCURACY_HIGH
                        criteria.horizontalAccuracy = Criteria.ACCURACY_HIGH
                        val bestLocationProvider = locationManager.getBestProvider(criteria, true)
                        if (bestLocationProvider != null) {
                            locationProvider = bestLocationProvider
                            locationManager.requestLocationUpdates(
                                locationProvider,
                                1000L,
                                0f,
                                locationListener
                            )
                        }
                    }
                }
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionRequestCode
                )
            }

            currentLocationLive.observe(activity) { currentLocation ->
                if (currentLocation != null) {
                    val currentSystemTime = System.currentTimeMillis()
                    val gpsTime = currentLocation.time
                    val timeDifference = abs(gpsTime - currentSystemTime) / 3600000
                    if (timeDifference > 1 && !wrongTimeDialog.isShowing) {
                        wrongTimeDialog.show()
                    }
                }
                if (!currentLocationLive.hasActiveObservers() && locationManager != null) {
                    locationManager.removeUpdates(locationListener)
                }
            }

            val lifecycleEventObserver = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_PAUSE) {
                    locationManager?.removeUpdates(locationListener)
                } else if (event == Lifecycle.Event.ON_RESUME) {
                    if (locationManager != null) {
                        if (isLocationEnabled(locationManager, disabledLocationDialog)) {
                            locationManager.requestLocationUpdates(
                                locationProvider,
                                1000L,
                                0f,
                                locationListener
                            )
                        }
                    }
                }
            }
            activity.lifecycle.addObserver(lifecycleEventObserver)
        }

        fun getUserStaffID(context: Context): String {
            val sharedPreferenceGeneral = SharedPreferenceGeneral(context)
            return sharedPreferenceGeneral.getValue(
                AppUser::class.java,
                Constants.KEY_CURRENT_APP_USER
            )?.getUserId().toString()
        }

        fun getAppUser(context: Context): AppUser? {
            val sharedPreferenceGeneral = SharedPreferenceGeneral(context)
            return sharedPreferenceGeneral.getValue(
                AppUser::class.java,
                Constants.KEY_CURRENT_APP_USER
            )
        }

        fun getUserHubID(context: Context): String {
            val sharedPreferenceGeneral = SharedPreferenceGeneral(context)
            return sharedPreferenceGeneral.getValue(
                AppUser::class.java,
                Constants.KEY_CURRENT_APP_USER
            )?.getHubId().toString()
        }

         */

        /**
         * Function to generate a QR code Bitmap
         * @param content: the content to be encoded into the QR code
         * @return Bitmap object that can be used in a view
         * */
     /*   fun generateQrCode(content: String): Bitmap {
            val barcodeEncoder = BarcodeEncoder()
            val qrDimen = 400
            return barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, qrDimen, qrDimen)
        }

      */

        fun compressImage(imageUri: String): String? {
            var scaledBitmap: Bitmap? = null
            val options = BitmapFactory.Options()

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(imageUri, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

//      max Height and width values of the compressed image is taken as 816x612
            val maxHeight = 816.0f * 2
            val maxWidth = 612.0f * 2
            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }

//      setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

//      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false

//      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)
            try {
//          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(imageUri, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

//      check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(imageUri!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                Log.d("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90F)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 3) {
                    matrix.postRotate(180F)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 8) {
                    matrix.postRotate(270F)
                    Log.d("EXIF", "Exif: $orientation")
                }
                scaledBitmap = Bitmap.createBitmap(
                    scaledBitmap, 0, 0,
                    scaledBitmap.width, scaledBitmap.height, matrix,
                    true
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var out: FileOutputStream? = null
            val filename = imageUri
            try {
                out = FileOutputStream(filename)

//          write the compressed bitmap at the destination specified by filename.
                scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return filename
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int,
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
            return inSampleSize
        }

        /**
         * Get the startDestination of the navGraph. To determine if the Activity should start from a
         * particular destination, or the default destination
         *
         * @param - The default start destination of the flow
         */
//        fun AppCompatActivity.getStartDestination(defaultStartDestination: Int): Int? {
//            return intent.extras?.getInt(
//                Constants.KEY_NAV_START_DESTINATION_EXTRA,
//                defaultStartDestination
//            )
//        }

        /**
         * Determine if the navigation into an Activity is from external module
         */
//        fun AppCompatActivity.getIsNavFromExternalModule(): Boolean? {
//            return intent.extras?.getBoolean(Constants.KEY_IS_NAV_FROM_EXTERNAL_MODULE_EXTRA, false)
//        }



        /**
         * Converts degree to radian representation
         */
        private fun deg2rad(deg: Double): Double {
            return deg * (Math.PI / 180)
        }

        /**
         * Rotate bitmap by specified angle
         */
        fun rotateBitmap(source: Bitmap, angle: Float): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        }

        /*

        fun showSingleActionDialog(
            context: Context,
            message: String,
            btnText: String? = context.getString(R.string.ok),
            isCancelable: Boolean = false,
            callback: () -> Unit = {},
        ) {
            val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(isCancelable)
            dialog.setContentView(R.layout.dialog_id_alert_custom)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textview = dialog.findViewById<TextView>(R.id.textView_event_location_succeccfully)
            textview.text = message

            val okBtn = dialog.findViewById<Button>(R.id.ok)
            okBtn.text = btnText
            okBtn.setOnClickListener {
                dialog.dismiss()
                callback()
            }

            dialog.show()
        }

         */

        /**
         * Get Adapter object based on the entered string array
         */
        fun getAdapter(context: Context, stringList: List<String>): ArrayAdapter<String> {
            return ArrayAdapter(
                context,
                R.layout.simple_dropdown_item_1line, stringList
            )
        }

        /**
         * Extension function to encrypt Strings
         **/
        fun String.encrypt(): String {
            val secret = "babbanGona123456"
            val secretIV = "1234567890123457"
            val iv = IvParameterSpec(secretIV.toByteArray())
            val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            val encryptedData = cipher.doFinal(this.toByteArray())
            val encodedByte = Base64.encode(encryptedData, Base64.DEFAULT)
            return String(encodedByte)
        }

        /**
         * Extension function to decrypt Strings
         **/
        fun String.decrypt(): String {
            val secret = "babbanGona123456"
            val secretIV = "1234567890123457"
            val decodedByte = Base64.decode(this, Base64.DEFAULT)
            val iv = IvParameterSpec(secretIV.toByteArray())
            val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            val output = cipher.doFinal(decodedByte)
            return String(output)
        }
    }
}