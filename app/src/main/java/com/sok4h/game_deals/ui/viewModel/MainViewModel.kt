package com.sok4h.game_deals.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IDealsRepository
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.DealDetailModel
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.ui_model.mappers.toDealModel
import com.sok4h.game_deals.ui.ui_model.mappers.toGameDetailModel
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
        getGamesFromDataBase()
    }


    fun searchGame() {

        _state.update {
            it.copy(isGameLoading = true, gameListState = emptyList())
        }
        viewModelScope.launch(Dispatchers.IO) {

            val result = gamesRepository.getGameDeals(state.value.searchQuery)
            val gamesFromNetwork = result.getOrDefault(emptyList())

            _state.update {
                it.copy(isGameLoading = false)
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


    private fun getDeals() {

        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, dealListState = mutableListOf())
            }

            dealsRepository.getListOfDeals(
                sortBy = state.value.sortDealsBy,
                lowerPrice = _state.value.minPrice.toIntOrNull(),
                upperPrice = _state.value.maxPrice.toIntOrNull()
            ).collect { result ->

                _state.update {
                    it.copy(isLoading = false)
                }
                if (result.isSuccess) {

                    _state.update {
                        it.copy(
                            dealListState = result.getOrDefault(mutableListOf()) as MutableList<DealDetailModel>
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


    private fun getGamesFromDataBase() {

        viewModelScope.launch(Dispatchers.IO) {

            gamesRepository.getGamesfromDatabase().collect { games ->

                if (games.isEmpty()) {

                    _state.update { it.copy(watchListState = emptyList()) }
                } else {
                    var ids = ""
                    games.forEachIndexed { index, item ->

                        ids += (if (index == games.lastIndex) {

                            item.gameId

                        } else "${item.gameId},")
                    }
                    val result = gamesRepository.getMultipleGames(ids)

                    if (result.isSuccess) {

                        val data = result.getOrDefault(emptyList())

                        val resultData = data.map { gameNetwork ->
                            val gameWithId =
                                games.find { it.name.contentEquals(gameNetwork.info.title) }


                            val deals = gameNetwork.deals.map { dealDto ->
                                val store = gamesRepository.getStorefromDatabase(dealDto.storeID)

                                dealDto.toDealModel(storeName = store.StoreName)
                            }

                            gameNetwork.toGameDetailModel(
                                gameWithId!!.gameId, isFavorite = true,
                                deals
                            )
                        }

                        _state.update {
                            it.copy(watchListState = resultData)
                        }

                    } else {

                        _state.update {
                            it.copy(
                                watchListErrorMessage = result.exceptionOrNull()?.message
                                    ?: "No error available"
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateQuery(query: String) {

        _state.update {
            it.copy(searchQuery = query)
        }

        if (query.isEmpty()) {
            _state.update {
                it.copy(gameListState = mutableListOf())
            }
        }
    }

    fun updateSortBy(sort: String,sortId:Int) {

        _state.update {
            it.copy(sortDealsBy = sort, sortDealsById = sortId)
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

