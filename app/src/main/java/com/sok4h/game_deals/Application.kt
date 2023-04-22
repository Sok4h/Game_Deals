package com.sok4h.game_deals

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sok4h.game_deals.data.di.appModule
import com.sok4h.game_deals.data.di.repositoryModules
import com.sok4h.game_deals.data.di.viewModelModules
import com.sok4h.game_deals.workers.DealWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class Application : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            workManagerFactory()
            modules(appModule, repositoryModules, viewModelModules)
        }
        setupWorkManagerFactory()

    }

    private fun setupWorkManagerFactory() {

        val work = PeriodicWorkRequestBuilder<DealWorker>(1,TimeUnit.DAYS).setConstraints(
            Constraints.Builder(

            ).setRequiredNetworkType(NetworkType.CONNECTED).build()


        ).build()

        WorkManager.getInstance(this).enqueue(work)
    }
}