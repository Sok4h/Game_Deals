package com.sok4h.game_deals.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.events.MainScreenEvents
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState
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
        MutableStateFlow(MainScreenState())
    val state get() = _state.asStateFlow()


    init {
        //getDeals()
    }

    fun setStateEvent(event: MainScreenEvents) {

        when (event) {

            is MainScreenEvents.SearchGames -> {

                _state.update {
                    it.copy(isLoading = true)
                }
                viewModelScope.launch(Dispatchers.IO) {

                    val result = gamesRepository.getGameDeals(state.value.searchQuery)
                    val gamesFromNetwork = result.getOrDefault(emptyList())
                    _state.update {
                        it.copy(isLoading = false)
                    }


                    if (result.isSuccess) {

                        gamesRepository.getGamesfromDatabase().collect {_->

                            val mappedList = gamesFromNetwork.map { networkGame ->

                                val isFavorite =
                                    gamesRepository.checkIfGameIsFavorite(networkGame.info.gameId)
                                networkGame.copy(info = networkGame.info.copy(isFavorite = isFavorite))
                            }

                            _state.update {

                                it.copy(
                                    gameListState = mappedList
                                )

                            }


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


            is MainScreenEvents.AddGametoWatchList -> {

                Log.e("TAG",  _state.value.dealListState.size.toString() )
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
            it.copy(isLoading = true)
        }
        viewModelScope.launch {

            dealsRepository.getListOfDeals().collect { result ->

                if (result.isSuccess) {

                    _state.update {
                        it.copy(dealListState = result.getOrDefault(mutableListOf()) as MutableList<DealDetailModel>)
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

    fun updateQuery(query: String) {

        _state.update {
            it.copy(searchQuery = query)
        }
    }

}

