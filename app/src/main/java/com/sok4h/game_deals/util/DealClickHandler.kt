package com.sok4h.game_deals.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun DealClickHandler(id:String) {

    val uriHandler = LocalUriHandler.current

    uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${id}")
}
