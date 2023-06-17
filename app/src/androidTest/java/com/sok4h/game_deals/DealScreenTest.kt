package com.sok4h.game_deals

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
class DealScreenTest {

    @get:Rule
    val composeRuleTest = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeRuleTest.onRoot(useUnmergedTree = true)
    }

    @Test
    fun filterDialogOpensCorrectly() {

        composeRuleTest.onNodeWithContentDescription("filter deals").performClick()
        composeRuleTest.onNodeWithTag("filter dialog").assertIsDisplayed()
    }

    @Test
    fun filterDialogClosesCorrectly() {


        composeRuleTest.onNodeWithContentDescription("filter deals").performClick()
        composeRuleTest.onNodeWithText("Update filters").performClick()
        composeRuleTest.onNodeWithTag("filter dialog").assertDoesNotExist()
    }


    @Test
    fun filterUpdatesValueCorrectly() {
        composeRuleTest.onNodeWithContentDescription("filter deals").performClick()
        composeRuleTest.onNodeWithTag("dropdown").performClick()
        composeRuleTest.onNodeWithText("Store").performClick()
        composeRuleTest.onNodeWithTag("dropdown").assertTextEquals("Store")
    }
}