package com.sok4h.game_deals

import com.sok4h.game_deals.data.model.dtos.DealDetailDto
import com.sok4h.game_deals.data.network.CheapSharkServiceImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ServiceTest {

    @get:Rule
    val mainDispatcher = CorroutineTestRule()
    var dealTest: DealDetailDto = DealDetailDto(
        "1", "", "", "", "", 0, "", "", "", "", "", "", "3", "Batman", ""
    )

    var service: CheapSharkServiceImpl = mockk(relaxed = true)


    @Test
    fun `Check if deals are received correctly`() = runBlocking {

        //given
        coEvery { service.getListOfDeals(null, null, null, null, null, null) } returns flow {
            emit(
                Response.success(
                    mutableListOf(
                        dealTest
                    )
                )
            )

        }

        val testlist = mutableListOf(dealTest)
        //when


        service.getListOfDeals(null, null, null, null, null, null).collect {

            //then
            TestCase.assertEquals(it.body(), testlist)
        }

    }

    @Test
    fun `when theres error in retrofit response, the flow emmits an error`() = runBlocking {


        //given
        coEvery { service.getListOfDeals(null, null, null, null, null, null) } returns flow {
            emit(
                Response.error(404, byteArrayOf().toResponseBody())
            )

        }

        //when
        service.getListOfDeals(null, null, null, null, null, null).collect {

            TestCase.assertEquals(it.isSuccessful, false)
        }
    }

}