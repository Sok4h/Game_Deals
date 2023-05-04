package com.sok4h.game_deals

import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import com.sok4h.game_deals.data.repositories.DealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.mappers.toDealDetailModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class RepositoryTests {

    @get:Rule
    val mainDispatcher = CorroutineTestRule()

    var dealTest: DealDetailDto = DealDetailDto(
        "1", "", "", "", "", 0, "", "", "", "", "", "", "3", "Batman", ""
    )

    var service: CheapSharkServiceImpl = mockk(relaxed = true)

    var gameRepository: IGamesRepository = mockk(relaxed = true)

    lateinit var dealsRepository: DealsRepository

    @Before
    fun setUp() {

        dealsRepository = DealsRepository(service)
    }

    @Test
    fun `when service call is succesfull the repository returns a list of dealDetailModel`() =
        runBlocking {


            coEvery { service.getListOfDeals(null, null, null, null, null, null) } returns flow {
                emit(
                    Response.success(
                        mutableListOf(
                            dealTest
                        )
                    )
                )

            }


            dealsRepository.getListOfDeals().collect {

                println(it)
                assertEquals(
                    it.getOrDefault(emptyList()),
                    mutableListOf(dealTest.toDealDetailModel())
                )
            }

        }

    @Test
    fun `when service call is unsuccessful it returns a error result`() = runBlocking {


        coEvery { service.getListOfDeals(null, null, null, null, null, null) } returns flow {
            emit(
                Response.error(
                    404, byteArrayOf().toResponseBody()
                )
            )

        }

        dealsRepository.getListOfDeals().collect {
            assertEquals(it.exceptionOrNull()!!.message, "404")
        }

    }
}
