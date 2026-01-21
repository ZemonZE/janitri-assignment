package com.pregnancy.vitals.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.pregnancy.vitals.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object AppModule {

    private const val REMINDER_WORK_NAME = "vitals_reminder_work"
    private const val TEST_WORK_NAME = "vitals_test_work"

    fun scheduleVitalsReminder(context: Context, testMode: Boolean = false) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        if (testMode) {
            // for testing: one-time work that runs after 1 minute
            val testRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueue(testRequest)
        } else {
            // for production: periodic work every 5 hours
            val request = PeriodicWorkRequestBuilder<ReminderWorker>(5, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                REMINDER_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, // don't reset timer on app restart
                request
            )
        }
    }
}
