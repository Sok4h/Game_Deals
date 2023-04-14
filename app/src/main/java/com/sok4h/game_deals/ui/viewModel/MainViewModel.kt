package com.sok4h.game_deals.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val gamesRepository: IGamesRepository,
    val dealsRepository: IDealsRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(MainScreenState())
    val state get() = _state.asStateFlow()


    init {
        getDeals()
    }


    fun searchGame() {

        _state.update {
            it.copy(isLoading = true, gameListState = emptyList())
        }
        viewModelScope.launch(Dispatchers.IO) {

            val result = gamesRepository.getGameDeals(state.value.searchQuery)
            val gamesFromNetwork = result.getOrDefault(emptyList())

            // TODO: solucionar esta madre, si lo hago con collect sale una lista fantasma

            _state.update {
                it.copy(isLoading = false)
            }

            if (result.isSuccess) {

                val mappedList = gamesFromNetwork.map { networkGame ->

                    val isFavorite = gamesRepository.checkIfGameIsFavorite(networkGame.info.gameId)
                    networkGame.copy(info = networkGame.info.copy(isFavorite = isFavorite))
                }

                _state.update {

                    it.copy(
                        gameListState = mappedList, gameListErrorMessage = ""
                    )

                }

            } else {

                _state.update {
                    it.copy(
                        gameListErrorMessage = result.exceptionOrNull()?.message
                            ?: "No error available", gameListState = emptyList()
                    )
                }
            }

        }

    }

    fun addGameToWatchList(game: GameDetailModel) {

        Log.e("TAG", _state.value.dealListState.size.toString())
        viewModelScope.launch(Dispatchers.IO) {

            gamesRepository.addGametoWatchList(game)

        }.invokeOnCompletion {

            val oldState = _state.value.gameListState

            val newList = oldState.map {

                if (it.info.gameId.contentEquals(game.info.gameId)) {

                    it.copy(info = it.info.copy(isFavorite = true))
                } else {

                    it
                }
            }

            _state.update {
                it.copy(gameListState = newList)
            }

        }
    }

    fun removeGameFromWatchlist(id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            gamesRepository.removeGamefromWatchlist(id)
        }.invokeOnCompletion {

            val oldState = _state.value.gameListState

            val newList = oldState.map {

                if (it.info.gameId.contentEquals(id)) {

                    it.copy(info = it.info.copy(isFavorite = false))
                } else {

                    it
                }
            }

            _state.update {
                it.copy(gameListState = newList)
            }
        }
    }


    fun getDeals() {

        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = false)
            }

            dealsRepository.getListOfDeals(
                sortBy = state.value.sortDealsBy,
                lowerPrice = _state.value.minPrice.toIntOrNull(),
                upperPrice = _state.value.maxPrice.toIntOrNull()
            ).collect { result ->

                if (result.isSuccess) {

                    _state.update {
                        it.copy(
                            dealListState = result.getOrDefault(mutableListOf())
                                .take(4) as MutableList<DealDetailModel>
                        )
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

    fun updateSortBy(sort: String) {

        _state.update {
            it.copy(sortDealsBy = sort)
        }
    }

    fun updateMinPrice(price: String) {
        _state.update {
            it.copy(minPrice = price)
        }
    }

    fun updateMaxPrice(price: String) {
        _state.update {
            it.copy(maxPrice = price)
        }
    }

    fun updateFilter() {
        getDeals()
    }

}

