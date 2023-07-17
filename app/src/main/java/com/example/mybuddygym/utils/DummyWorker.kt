package com.example.mybuddygym.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DummyWorker(mContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(mContext, workerParams) {
    override suspend fun doWork(): Result {
        return Result.success()
    }
}
