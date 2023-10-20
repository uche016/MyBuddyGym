package com.example.mybuddygym.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 *
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
class AppExecutors private constructor(
    private val _diskIO: Executor,
    private val _networkIO: Executor,
    private val _mainThread: Executor
) {

    companion object {
        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: AppExecutors? = null
        private const val NETWORK_POOL_SIZE: Int = 3

        /**
         * Get App Executor single Instance
         */
        private fun getInstance(): AppExecutors? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = AppExecutors(
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(NETWORK_POOL_SIZE),
                        MainThreadExecutor()
                    )
                }
            }
            return sInstance
        }

        fun getDiskIO(): Executor = getInstance()!!._diskIO
        fun getNetworkIO(): Executor = getInstance()!!._networkIO
        fun getMainThreadIO(): Executor = getInstance()!!._mainThread
    }

    /**
     * Executor class that executes tasks on the main thread
     */
    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
