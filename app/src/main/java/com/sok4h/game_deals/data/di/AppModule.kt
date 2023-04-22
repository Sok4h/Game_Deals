package com.sok4h.game_deals.data.di

import androidx.room.Room
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.sok4h.game_deals.data.database.GameDealsDatabase
import com.sok4h.game_deals.data.network.CheapSharkAPI
import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import com.sok4h.game_deals.data.repositories.DealsRepository
import com.sok4h.game_deals.data.repositories.GamesRepository
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.workers.DealWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

val appModule = module {

    val BASEURL = "https://www.cheapshark.com/api/1.0/"
    single {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheapSharkAPI::class.java)
    }

    single {

        Room.databaseBuilder(
            androidContext(),
            GameDealsDatabase::class.java, "GameDealDatabase"
        ).createFromAsset("database/store.db").build()
    }

    single {

        get<GameDealsDatabase>().gameDao()

    }
    single {
        get<GameDealsDatabase>().storeDao()
    }

    single {
        CheapSharkServiceImpl(get())
    }
    worker { DealWorker(get(),get()) }

}


val repositoryModules = module {

    single<IGamesRepository> { GamesRepository(get(), get(), get()) }

    single<IDealsRepository> { DealsRepository(get()) }


}

val viewModelModules = module {

    viewModel { MainViewModel(get(), get()) }
}