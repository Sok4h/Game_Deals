package com.sok4h.game_deals.events

import com.sok4h.game_deals.ui.ui_model.GameDetailModel

sealed interface MainScreenEvents {

    object SearchGames : MainScreenEvents

    class AddGametoWatchList(val game: GameDetailModel) : MainScreenEvents

    class RemoveFromWatchList(val id: String) : MainScreenEvents
}
