package com.sok4h.game_deals.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState
import com.sok4h.game_deals.utils.DealState
import com.sok4h.game_deals.utils.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val gamesRepository: IGamesRepository,
    val dealsRepository: IDealsRepository,
) :
    ViewModel() {

    private var _state =
        MutableStateFlow(MainScreenState(GameState.Loading, DealState.Loading, true))
    val state get() = _state.asStateFlow()


    init {
        getDeals()
    }

    fun setStateEvent(event: MainScreenEvents) {

        when (event) {

            is MainScreenEvents.SearchGames -> {

                _state.update {
                    it.copy(gameListState = GameState.Loading)
                }
                viewModelScope.launch(Dispatchers.IO) {


                    gamesRepository.getGamesfromDatabase().collect {
                        Log.e("TAG", "collected")
                        val result = gamesRepository.getGameDeals(state.value.searchQuery)

                        if (result.isSuccess) {

                            val gamesFromNetwork = result.getOrDefault(emptyList())
                            gamesFromNetwork.map { networkGame ->

                                val favorite = it.any { it.gameId == networkGame.info.gameId }
                                networkGame.info.isFavorite = favorite


                            }


                            _state.update {

                                Log.e("TAG", "setStateEvent: ")
                                it.copy(
                                    gameListState = GameState.Success(
                                        gamesFromNetwork
                                    )
                                )
                            }


                        } else {

                            _state.update {
                                it.copy(gameListState = GameState.Error(result.exceptionOrNull() as Exception))
                            }
                        }

                    }

                }


            }
            is MainScreenEvents.AddGametoWatchList -> {

                viewModelScope.launch {

                    gamesRepository.addGametoWatchList(event.game)
                }

            }
            is MainScreenEvents.RemoveFromWatchList -> {

                viewModelScope.launch(Dispatchers.IO) {
                    gamesRepository.removeGamefromWatchlist(event.id)
                }
            }
        }
    }

    fun getDeals() {

        _state.update {
            it.copy(dealListState = DealState.Loading)
        }
        viewModelScope.launch {

            dealsRepository.getListOfDeals().collect { result ->

                if (result.isSuccess) {

                    _state.update {
                        it.copy(dealListState = DealState.Success(result.getOrDefault(emptyList())))
                    }
                } else {

                    _state.update {
                        it.copy(dealListState = DealState.Error(result.exceptionOrNull() as Exception))
                    }
                }

            }
        }


    }

    fun updateQuery(query: String) {

        _state.update {
            it.copy(searchQuery = query)
        }
    }

}

sealed interface MainScreenEvents {

    object SearchGames : MainScreenEvents

    class AddGametoWatchList(val game: GameDetailModel) : MainScreenEvents

    class RemoveFromWatchList(val id: String) : MainScreenEvents
}