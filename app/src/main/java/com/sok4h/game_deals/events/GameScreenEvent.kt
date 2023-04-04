package com.sok4h.game_deals.events

sealed class GameScreenEvent{

    object SearchGameEvent: GameScreenEvent()
    data class AddToWatchListClicked(val id:String): GameScreenEvent()
    object DealClicked: GameScreenEvent()
    data class SearchQueryUpdated(val query:String):GameScreenEvent()

}
