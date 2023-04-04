package com.sok4h.game_deals

import android.app.Application
import com.sok4h.game_deals.data.di.appModule
import com.sok4h.game_deals.data.di.repositoryModules
import com.sok4h.game_deals.data.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(appModule, repositoryModules, viewModelModules)

        }
    }
}