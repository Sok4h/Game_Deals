package com.sok4h.game_deals.events

sealed class WatchListScreenEvent{

    object DealClicked: WatchListScreenEvent()
    data class RemoveFromWatchList(val id:String):WatchListScreenEvent()

}