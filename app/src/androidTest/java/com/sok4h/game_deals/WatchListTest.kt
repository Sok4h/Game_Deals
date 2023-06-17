package com.sok4h.game_deals

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sok4h.game_deals.ui.screens.WatchListScreen
import com.sok4h.game_deals.ui.theme.Game_DealsTheme
import com.sok4h.game_deals.ui.ui_model.CheapestPriceEverModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.ui_model.InfoModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
class WatchListTest {


    @get:Rule
    val composeRuleTest = createComposeRule()

    var testList = GameDetailModel(
        CheapestPriceEverModel(0, ""),
        emptyList(),
        InfoModel("", "", true, ""), ""
    )

    @Test
    fun when_no_games_in_favorites_empty_message_is_displayed() {


        composeRuleTest.setContent {
            WatchListScreen(state = MainScreenState(), onRemoveFromWatchList ={} )
        }

        composeRuleTest.onNodeWithTag("No games Text").assertIsDisplayed()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun when_game_list_has_items_empty_message_doesnt_show() = runTest {

        val state = MainScreenState(
            watchListState = listOf(
                testList

            )
        )
        composeRuleTest.setContent {
            Game_DealsTheme() {
                WatchListScreen(state) {}
            }
        }

        composeRuleTest.onNodeWithTag("No games Text").assertDoesNotExist()

    }

    @Test
    fun progressBarShowsCorrectly() {
        composeRuleTest.setContent {
            Game_DealsTheme() {
                WatchListScreen(MainScreenState(isWatchlistLoading = true)) {}
            }
        }

        composeRuleTest.onNodeWithTag("loading watchlist").assertIsDisplayed()
    }
}