package com.example.mybuddygym.utils

import android.content.Context

class IdGenerator private constructor(
    private val appUser: AppUser,
) {
    fun getId(tableName: String, prefix: String? = null, suffix: String = ""): String {
        val idPrefix: String = "AS"
        val userId = getMinifiedUserId(appUser.getUserId())
        var id = "${idPrefix}${userId}${AppUtils.getDateString(Constants.KEY_DATE_FORMAT_CONCAT)}"
        if (suffix.isNotEmpty()) {
            id += "_$suffix"
        }
        return id
    }

    /**
     * Returns a minified form of the user id
     * For instance T-100000000000013 will become T-00013
     * Method only minifies ids of length>=7
     */
    private fun getMinifiedUserId(userId: String): String {
        if (userId.length < 7 || userId.startsWith("IK", true)) {
            return userId
        }
        return userId.substring(0, 2) + userId.substring(userId.length - 5, userId.length)
    }

    /**
     * Return table name capitalized initials as prefix
     * It is assumed table name is snake-cased
     */
    private fun getPrefix(tableName: String): String {
        var words = tableName.split("_")
        var tableInitials = ""
        for (word in words) {
            tableInitials += word[0]
        }
        return tableInitials.uppercase()
    }

    fun getLastNCharsOfString(str: String, n: Int): String? {
        var lastnChars = str
        if (lastnChars.length > n) {
            lastnChars = lastnChars.substring(lastnChars.length - n, lastnChars.length)
            return lastnChars
        }
        return str
    }

    companion object {
        private val TAG = IdGenerator::class.java.simpleName
        private var instance: IdGenerator? = null

        /**
         * Access the IdGenerator instance via singleton pattern
         */
        fun getInstance(context: Context): IdGenerator {
            if (instance == null) {
                synchronized(IdGenerator::class) {
                    val appUser: AppUser =
                        SharedPreferenceMyBuddyApp(context).getValue(
                            AppUser::class.java,
                            Constants.KEY_CURRENT_APP_USER
                        )!!
                    instance = IdGenerator(appUser)
                    return instance!!
                }
            }
            return instance!!
        }
    }
}
