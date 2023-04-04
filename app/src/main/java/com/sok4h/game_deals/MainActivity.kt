package com.sok4h.game_deals

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.theme.Game_DealsTheme
import com.sok4h.game_deals.ui.ui_model.GameDetailModel
import com.sok4h.game_deals.ui.viewModel.MainScreenEvents
import com.sok4h.game_deals.ui.viewModel.MainViewModel
import com.sok4h.game_deals.ui.viewStates.MainScreenState
import com.sok4h.game_deals.utils.DealState
import com.sok4h.game_deals.utils.GameState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Game_DealsTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by mainViewModel.state.collectAsState()
                    MainScreen(
                        state = state, mainViewModel::updateQuery,
                        onAddToWatchList = {mainViewModel.setStateEvent(MainScreenEvents.AddGametoWatchList(it))},
                        onSearch = { mainViewModel.setStateEvent(MainScreenEvents.SearchGames) },
                        onRemoveFromWatchList = {mainViewModel.setStateEvent(MainScreenEvents.RemoveFromWatchList(it))}
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onAddToWatchList: (GameDetailModel) -> Unit,
    onRemoveFromWatchList: (String) -> Unit,
) {


    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        TextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.searchQuery,
            onValueChange = onQueryChanged,
            trailingIcon = {
                IconButton(
                    onClick = onSearch
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
                }
            })


        when (state.gameListState) {
            is GameState.Error -> {}
            GameState.Loading -> {

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(25.dp),
                        strokeWidth = 2.dp, color = Color.Red
                    )
                }

            }
            is GameState.Success -> {


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxHeight(0.8f)
                ) {

                    items(items = state.gameListState.data) {


                        GameDealCard(game = it, onAddToWatchList = onAddToWatchList, onRemoveFromWatchList = onRemoveFromWatchList)


                    }
                }

            }
        }

        when (state.dealListState) {
            is DealState.Error -> {

                Text(text = state.dealListState.error.message ?: "xd")
            }
            DealState.Loading -> Text(text = "Cargando")
            is DealState.Success -> Text(text = "Exito")
        }

    }


}


@Composable
fun GameDealCard(
    game: GameDetailModel,
    onAddToWatchList: (GameDetailModel) -> Unit,
    onRemoveFromWatchList: (String) -> Unit,
) {
    Log.e("TAG", game.info.isFavorite.toString() )
    Game_DealsTheme {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = game.info.image,
                contentDescription = "image of ${game.info.title}",
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start

            ) {


                Text(
                    text = game.info.title ?: "No available",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = game.deals.size.toString() + " Deals",
                    style = MaterialTheme.typography.bodySmall
                )


            }

            Text(text = game.bestPrice, modifier = Modifier.padding(end = 8.dp))

            IconButton(onClick = {
                if (game.info.isFavorite) {
                    onRemoveFromWatchList(game.info.gameId)
                }else{

                    onAddToWatchList(game)
                }
            }) {

                if (game.info.isFavorite) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "remove from watchlist"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "save to watchlist"
                    )

                }
            }
        }
    }


}

@Preview
@Composable
fun GameDealCardPreview() {

    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .background(Color.Yellow),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            contentScale = ContentScale.Fit,
        )
        Column(
            modifier = Modifier
                .background(Color.Red)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start

        ) {
            Box {
                Text(
                    text = "Lego Batman: The videogameeeeeeeeeeeeeeeeee",
                    style = MaterialTheme.typography.bodyLarge,

                    )
            }



            Text(
                text = " 4 Deals",
                style = MaterialTheme.typography.bodySmall
            )


        }

        Text(text = "14.99", modifier = Modifier.padding(horizontal = 8.dp))
    }

}