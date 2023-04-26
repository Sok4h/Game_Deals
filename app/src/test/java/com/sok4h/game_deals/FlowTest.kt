package com.sok4h.game_deals

import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import com.sok4h.game_deals.data.repositories.DealsRepository
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcher = CorroutineTestRule()

    lateinit var mainViewmodel: MainViewModel
    var dealTest: DealDetailDto = DealDetailDto(
        "1", "", "", "", "", 0, "", "", "", "", "", "", "", "Batman", ""
    )

    var service: CheapSharkServiceImpl = mockk(relaxed = true)
    var dealsRepository: IDealsRepository = mockk(relaxed = true)
    var gameRepository: IGamesRepository = mockk(relaxed = true)


    @Before
    fun setUp() {
        dealsRepository = DealsRepository(service)
        //mainViewmodel = MainViewModel(gameRepository, dealsRepository)

    }

    @Test
    fun `Check if deals are received correctly`() = runBlocking {

        coEvery { service.getListOfDeals(null, null, null, null, null, null) } returns flow {
            emit(
                Response.success(
                    mutableListOf(
                        dealTest
                    )
                )
            )

        }

        var testlist = mutableListOf(dealTest)
        //given

/*
        service.getListOfDeals(null,null,null,null,null,null).collect{

            println(it.body())
        }*/

dealsRepository.getListOfDeals().collectLatest {

    println(it)
}



    }


}
