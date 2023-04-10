package com.sok4h.game_deals.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.events.WatchListScreenEvent
import com.sok4h.game_deals.ui.ui_model.mappers.toGameDetailModel
import com.sok4h.game_deals.ui.viewStates.WatchListScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchListViewModel(var gamesRepository: IGamesRepository) : ViewModel() {

    private val _state = MutableStateFlow(WatchListScreenState())
    val state get() = _state.asStateFlow()

    init {
        getGamesFromDataBase()
    }

    private fun getGamesFromDataBase() {

        viewModelScope.launch {

            gamesRepository.getGamesfromDatabase().collect { games ->

                Log.e("WatchList", (_state.value.gameListState.size - 1).toString())
                Log.e("WatchList", "actualizado")
                var ids = ""
                games.forEachIndexed { index, item ->

                    ids += (if (index == games.size - 1) {

                        item.gameId

                    } else "${item.gameId},")
                }
                val result = gamesRepository.getMultipleGames(ids)

                if (result.isSuccess) {

                    val data = result.getOrDefault(emptyList())

                    val resultData = data.map { gameNetwork ->
                        val gameWithId =
                            games.find { it.name.contentEquals(gameNetwork.info.title) }

                        gameNetwork.toGameDetailModel(gameWithId!!.gameId, isFavorite = true)
                    }

                    _state.update {
                        it.copy(gameListState = resultData.toMutableList())
                    }

                } else {

                    _state.update {
                        it.copy(
                            gameListErrorMessage = result.exceptionOrNull()?.message
                                ?: "No error available"
                        )
                    }
                }
            }
        }
    }

    fun setEvent(event: WatchListScreenEvent) {

        when (event) {
            WatchListScreenEvent.DealClicked -> {}
            is WatchListScreenEvent.RemoveFromWatchList -> {

                Log.e("TAG", "Eliminar")
                viewModelScope.launch(Dispatchers.IO) {

                    gamesRepository.removeGamefromWatchlist(event.id)

                }

            }
        }
    }

}



