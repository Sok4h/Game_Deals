package com.sok4h.game_deals.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Sell
import androidx.compose.ui.graphics.vector.ImageVector
import com.sok4h.game_deals.R

sealed class BottomBarScreens(
    val route:String,
    @StringRes val title:Int,
    val icon:ImageVector

){
    object Favorites:BottomBarScreens(route = "favorite", R.string.favorites,Icons.Default.Favorite)

    object Deals:BottomBarScreens(route = "dealScreen",R.string.deals,Icons.Default.Sell)
}
