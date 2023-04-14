package com.sok4h.game_deals.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.mappers.toDealModel
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

        viewModelScope.launch(Dispatchers.IO) {

            gamesRepository.getGamesfromDatabase().collect { games ->

                if (games.isEmpty()) {

                    _state.update { it.copy(gameListState = emptyList()) }
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
                            it.copy(gameListState = resultData)
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
    }



}



