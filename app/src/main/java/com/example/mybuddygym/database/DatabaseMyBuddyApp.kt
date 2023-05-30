package com.example.mybuddygym.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mybuddygym.database.model.user.User
import com.example.mybuddygym.database.model.user.UserDao
import com.example.mybuddygym.database.model.workOutDetails.WorkOutDetails
import com.example.mybuddygym.database.model.workOutDetails.WorkOutDetailsDao
import timber.log.Timber


@Database(
    entities = [
        User::class,
       WorkOutDetails::class,
    ],

    version = 1, exportSchema = true
)
abstract class DatabaseMyBuddyApp : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun workOutDetailsDao(): WorkOutDetailsDao

    // static access
    companion object {
        @Volatile
        private var sInstance: DatabaseMyBuddyApp? = null
        private const val DATABASE_NAME = "MyBuddyApp.db"

        // singleton pattern
        fun getInstance(context: Context): DatabaseMyBuddyApp {
            if (sInstance == null) {
                synchronized(DatabaseMyBuddyApp::class) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseMyBuddyApp::class.java,
                        DATABASE_NAME
                    )
                        // .allowMainThreadQueries()
                        .addMigrations(

                        )
                        // .allowMainThreadQueries()
                        // Migrations should be put here
                        // .fallbackToDestructiveMigration() // If you want room to re-create all of the tables when app version changes.
                        .addCallback(sRoomDatabaseCallback) // To manually populate the database.
                        .build()
                }
            }
            return sInstance!!
        }

        /**
         * Override the onCreate method to populate the database.
         * For this sample, we insert a metric entry (in the background thread)
         * every time the database is created.
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // TODO: Remove this once data starts coming in
                Timber.d("Preloading Collection Center data")
                /** Populate the database in the background. */

            }


            }
        }
    }






