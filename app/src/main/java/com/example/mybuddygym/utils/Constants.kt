package com.example.mybuddygym.utils

class Constants private constructor() {
    companion object {

        // Identifier for shared preference.
        private const val PREFERENCE_FILE_KEY = "AssetManager"
        const val PACKAGE_NAME = "com.babbangona.assetmanager"
        const val APP_IDENTIFIER = "$PACKAGE_NAME.$PREFERENCE_FILE_KEY"
        const val HOME_PAGE_INITIAL_SYNC_TAG = "home_page_sync"
        const val IS_DATA_PRELOADED = "is_data_preloaded"
        const val IS_FROM_IMAGE_BUNDLER = "is_from_image_bundler"
        const val IS_MEMBER_IMAGES_PRELOADED = "is_member_images_preloaded"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_CURRENT_APP_USER = "CURRENT_APP_USER"
        const val ADDED_DETAILS ="ADDED_DETAILS"

        /** File constants */
        const val NO_MEDIA_FILE = ".nomedia"
        const val ROAD_USER_PICTURE_DIR = "Road_User"
        const val KEY_ASSET_NO_STATE= "key_asset_no_state"
        const val UNIQUE_IDENTIFIERS = "unique_identifiers"
        const val STAFF_PICTURE_DIR = "Staffs"
        const val STAFF_PICTURE_SUFFIX = "_thumb.jpg"
        const val ASSET_PHOTO ="asset_photo"
        const val STAFF_BGFR_RECAPTURES_DIR = "BGFR-Recaptures/Staffs"
        const val BGFR_STATE = "bgfr_state"
        const val KEY_RECAPTURED_TEMPLATE = "RECAPTURED_TEMPLATE"


        /** date time constants */
        const val KEY_DATE_FORMAT_CONCAT = "DATE_FORMAT_CONCAT"
        const val KEY_DATE_FORMAT_SPREAD = "DATE_FORMAT_SPREAD"
        const val KEY_DATE_FORMAT_SLASHED_DMY = "DATE_FORMAT_SLASHED_DMY"
        const val KEY_DATE_FORMAT_DEFAULT = "DATE_FORMAT_DEFAULT"
        const val KEY_TIME_FORMAT_DEFAULT = "TIME_FORMAT_DEFAULT"
        const val KEY_DATE_FORMAT_UI_FULL = "DATE_FORMAT_UI_FULL"
        const val KEY_DATE_FORMAT_UI_MEDIUM = "DATE_FORMAT_UI_MEDIUM"
        const val KEY_DATE_FORMAT_SYSTEM = "DATE_FORMAT_SYSTEM"
        const val KEY_DATE_FORMAT_DEFAULT_FILE = "DATE_FORMAT_DEFAULT_FILE"
        const val KEY_DATE_FORMAT_CONCAT2 = "DATE_FORMAT_CONCAT2"
        const val KEY_DATE_FORMAT_HYPHEN_DMY = "DATE_FORMAT_HYPHEN_DMY"

        const val DATE_FORMAT_CONCAT = "yyMMddHHmmss"
        const val DATE_FORMAT_SPREAD = "yyyy-MM-dd HH:mm:ss" // e.g 2021-01-05 15:00:00
        const val DATE_FORMAT_SLASHED_DMY = "dd/MM/yyyy" // e.g 05/01/2021
        const val DATE_FORMAT_DEFAULT = "yyyy-MM-dd" // e.g 2021-01-05
        const val DATE_FORMAT_DEFAULT_FILE = "yyyyMMdd" // e.g 20210105
        const val TIME_FORMAT_DEFAULT = "HHmmss" // e.g 150000
        const val DATE_FORMAT_UI_FULL = "EEEE, MMM d, yyyy" // e.g Tuesday, Jan 5, 2021
        const val DATE_FORMAT_UI_MEDIUM = "d MMMM yyyy" // e.g 5 January 2021
        const val DATE_FORMAT_SYSTEM = "yyyy-MM-dd'T'HH:mm:ss.SSS" // e.g 2022-04-07T09:51:03.670
        const val TIMEZONE_GMT = "GMT"
        const val DATE_FORMAT_HYPHEN_DMY = "dd-MM-yyyy" // 10-01-2022
        const val DATE_FORMAT_CONCAT2 = "yyMMddHHmmssSSS"
        const val DATE_FORMAT_WITH_DAY = "yyyy-MM-dd (EEEE)" // e.g 2022-07-15 (Friday)

        val SESSION_KEYS by lazy {
            listOf(
                SELECTED_STAFF_ID,
                SELECTED_ASSET_ID,
                SELECTED_UNIQUE_ASSET_ID,
                SELECTED_ASSIGNED_ASSET
            )
        }


        /** SESSION KEYS **/
        const val SELECTED_STAFF_ID = "selected_staff_id"
        const val SELECTED_ASSET_ID = "selected_asset_id"
        const val SELECTED_UNIQUE_ASSET_ID = "selected_unique_asset_id"
        const val SELECTED_ASSIGNED_ASSET = "selected_assigned_asset"

        /** Staff levels **/

        const val MIL_SECS_IN_DAY = 86_400_000
        const val MIL_SECS_IN_MINUTE = 60_000
        const val HOURS_IN_DAY = 24

        const val KEY_FIRST_MEDIA_SYNC_STATE = "first_media_sync_state"

        const val NO_DATA = "no data to sync"
        const val HTTP_ERROR_MSG = "Server error occurred"
        const val NETWORK_ERROR_MSG = "Network error occurred"
        const val UNKNOWN_ERROR_MSG = "Unknown error occurred"

        /** Navigation constants */
        const val KEY_NAV_START_DESTINATION_EXTRA = "AssetManager.navigationId-extra"
        const val KEY_IS_NAV_FROM_EXTERNAL_MODULE_EXTRA = "AssetManager.isFromExternalModule-extra"

        /** Navigation constants */

        /** FILE SYNC CONSTANTS **/
        const val ASSET_MANAGER_FILE_TABLE_NAME = "Asset Manager File"
        const val ASSET_MANAGER_FILE_DOWNLOAD_UNIQUE_WORKER_NAME = "Asset Manager File Download"


    }
}