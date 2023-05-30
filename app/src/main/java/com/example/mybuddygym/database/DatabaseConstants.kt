package com.example.mybuddygym.database

object DatabaseConstants  {

    object UserTable {
        const val TABLE_NAME = "user"

        object ColumnNames {
            const val USER_ID = "user_id"

            const val FIRST_NAME = "first_name"
            const val LAST_NAME = "last_name"
            const val HUB = "hub"
            const val TEMPLATE = "template"
            const val PASSWORD = "password"
            const val ACCESS_LEVEL = "access_level"
        }
    }



    object AppConfigTable {
        const val TABLE_NAME = "app_config"

        object ColumnNames {
            const val KEY = "key"

            const val VALUE = "value"
        }
    }

    object WorkOutDetailsTable {
        const val TABLE_NAME = "work_out_details"

        object ColumnNames {
            const val APP_USER_ID = "app_user_id"

            const val ROAD_USER_NAME = "app_user_name"
            const val SEX = "sex"
            const val DATE_OF_BIRTH = "date_of_birth"
            const val HOME_ADDRESS = "home_address"
            const val HEIGHT = "height"
            const val BLOOD_GROUP = "blood_group"
            const val NEXT_OF_KIN = "next_of_kin"
            const val PHONE_NUMBER = "phone_number"
            const val APP_VERSION  = "app_version"
            const val IMEI  = "imei"
            const val SYNC_FLAG  = "sync_flag"
        }
    }


}