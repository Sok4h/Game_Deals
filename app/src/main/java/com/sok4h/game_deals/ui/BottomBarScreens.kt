package com.sok4h.game_deals.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Sell
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(
    val route:String,
    val title:String,
    val icon:ImageVector

){
    object WatchList:BottomBarScreens(route = "favorite","Favorites",Icons.Default.Favorite)

    object Deals:BottomBarScreens(route = "dealScreen","Deals",Icons.Default.Sell)
}
