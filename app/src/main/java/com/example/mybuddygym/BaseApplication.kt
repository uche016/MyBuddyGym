package com.example.mybuddygym

import android.app.Application
import com.example.mybuddygym.database.di.ApplicationComponent
import com.flurry.android.FlurryAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class BaseApplication : Application() {

    lateinit var appComponent: ApplicationComponent
    private val applicationScope = CoroutineScope(Dispatchers.Default)


}
