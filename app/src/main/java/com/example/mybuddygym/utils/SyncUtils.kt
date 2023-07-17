package com.example.mybuddygym.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.WorkInfo
import androidx.work.WorkManager


object SyncUtils {

    const val UPLOAD_FAILURE_MESSAGE = "Data failed to upload try again"

    // Add all download worker tags here
//    private val downloadWorkerTagList = listOf(
//
//    )

    /**
     * Sets up an observer within activity or fragment to monitor work state
     * to display a loading spinner when the workers are running
     * @param context: The context of the activity or fragment
     * @param firstWorkerTag: the tag of the first workers to be called
     * @param finalWorkerTag: the tag of the final worker to be called
     * @param swipeRefreshLayout: The refresh layout for the sync observer
     */
    fun setupSyncObserver(
        context: Context,
        firstWorkerTag: String,
        finalWorkerTag: String,
        swipeRefreshLayout: SwipeRefreshLayout,
        secondSyncFunction: () -> Unit,
    ) {
        val toastMessage = 2
//            if (firstWorkerTag in downloadWorkerTagList) {
//                "SyncDown Completed"
//            } else {
//                "SyncUp Completed"
//            }
        val lifecycleOwner = context as LifecycleOwner
        val workManager = WorkManager.getInstance(context)
        val firstWorkInfos = workManager.getWorkInfosByTagLiveData(firstWorkerTag)
        val lastWorkInfos = workManager.getWorkInfosByTagLiveData(finalWorkerTag)
        firstWorkInfos.observe(lifecycleOwner) {

            if (!it.isNullOrEmpty()) {
                val firstWorkInfo = it[0]
                if (firstWorkInfo.state == WorkInfo.State.RUNNING) {
                    swipeRefreshLayout.isRefreshing = true
                }
            }
        }
        lastWorkInfos.observe(lifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val lastWorkInfo = it[0]
                if (lastWorkInfo.state == WorkInfo.State.SUCCEEDED || lastWorkInfo.state == WorkInfo.State.FAILED) {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    firstWorkInfos.removeObservers(lifecycleOwner)
                    lastWorkInfos.removeObservers(lifecycleOwner)
                    secondSyncFunction()
                }
            }
        }
    }

    /**
     * Sets up an observer within activity or fragment to monitor work state
     * to display a loading spinner when the workers are running
     * @param context: The context of the activity or fragment
     * @param firstWorkerTag: the tag of the first workers to be called
     * @param finalWorkerTag: the tag of the final worker to be called
     * @param toastMessage: the toast message on completion
     * @param swipeRefreshLayout: The refresh layout for the sync observer
     */
    fun setupSyncObserver(
        context: Context,
        firstWorkerTag: String,
        finalWorkerTag: String,
        swipeRefreshLayout: SwipeRefreshLayout,
        toastMessage: String,
        toastContext: Context,
        secondSyncFunction: () -> Unit,
    ) {
        val lifecycleOwner = context as LifecycleOwner
        val workManager = WorkManager.getInstance(context)
        val firstWorkInfos = workManager.getWorkInfosByTagLiveData(firstWorkerTag)
        val lastWorkInfos = workManager.getWorkInfosByTagLiveData(finalWorkerTag)
        firstWorkInfos.observe(lifecycleOwner) {

            if (!it.isNullOrEmpty()) {
                val firstWorkInfo = it[0]
                if (firstWorkInfo.state == WorkInfo.State.RUNNING) {
                    swipeRefreshLayout.isRefreshing = true
                }
            }
        }
        lastWorkInfos.observe(lifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val lastWorkInfo = it[0]
                if (lastWorkInfo.state == WorkInfo.State.SUCCEEDED || lastWorkInfo.state == WorkInfo.State.FAILED) {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    firstWorkInfos.removeObservers(lifecycleOwner)
                    lastWorkInfos.removeObservers(lifecycleOwner)
                    secondSyncFunction()
                }
            }
        }
    }



    object GeneralTables {
        const val moduleName = "general"

        object TableNames {
            const val MEMBER_HA_UPDATE_GENERAL = "member_ha_update_general"
        }
    }
}
