package com.sok4h.game_deals.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.ui_model.GameDetailModel

@Composable
fun GameDealCard(
    game: GameDetailModel,
    onAddToWatchList: (GameDetailModel) -> Unit,
    onRemoveFromWatchList: (String) -> Unit,
    onDealPressed: (String) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()


    ) {
        Row(
            Modifier
                .clickable {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {

            AsyncImage(
                model = game.info.image,
                contentDescription = "image of ${game.info.title}",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start

            ) {


                Text(
                    text = game.info.title ?: "No available",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = game.deals.size.toString() + " Deals",
                    style = MaterialTheme.typography.bodySmall
                )

            }

            Text(text = "$ ${game.bestPrice}", modifier = Modifier,style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = {
                if (game.info.isFavorite) {
                    onRemoveFromWatchList(game.info.gameId)
                } else {

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

        AnimatedVisibility(visible = expanded) {
            Column() {

                for (deal in game.deals) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        AsyncImage(
                            model = deal.storeImage,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )

                        Text(text = deal.storeName!!,Modifier.weight(1.5f))
                        Text(text = "$" + deal.price,Modifier.weight(1f))

                        TextButton(onClick = { onDealPressed(deal.dealID) }) {

                            Text(text = "Comprar")
                        }

                    }
                }
            }

        }
    }
}



