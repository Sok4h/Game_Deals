package com.sok4h.game_deals.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.mappers.toGameDetailModel
import com.sok4h.game_deals.utils.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchListViewModel(var gamesRepository: IGamesRepository) : ViewModel() {

    private val _state = MutableStateFlow<GameState>(GameState.Loading)
    val state get() = _state.asStateFlow()

    init {
        getGamesFromDataBase()
    }

    fun getGamesFromDataBase() {

        viewModelScope.launch {

            gamesRepository.getGamesfromDatabase().collect { games ->

                var ids= ""
                games.forEachIndexed{index,item->

                    ids += if(index==games.size-1){

                        item.gameId

                    }else{

                        "${item.gameId},"
                    }
                }
                val result =gamesRepository.getMultipleGames(ids)

              if(result.isSuccess) {

                  val data = result.getOrDefault(emptyList())

                  if(data.isNotEmpty()){

                      val result= data.map {gameNetwork->


                          val gameWithId = games.find { it.name.contentEquals(gameNetwork.info.title) }

                          gameNetwork.toGameDetailModel(gameWithId!!.gameId,true)
                      }

                      _state.value = GameState.Success(result)
                  }

                  else{

                      _state.value = GameState.Success(emptyList())
                  }

              }

                else{

                    _state.value = GameState.Error(result.exceptionOrNull() as Exception)
                }
            }
        }
    }

}



