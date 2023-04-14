package com.sok4h.game_deals.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.ui_model.GameDetailModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDealCard(
    game: GameDetailModel,
    onAddToWatchList: (GameDetailModel) -> Unit,
    onRemoveFromWatchList: (String) -> Unit,
    onDealPressed: (String) -> Unit,
) {

    // TODO: añadir el precio normal a las ofertas?  
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            , onClick = {expanded = !expanded}


    ) {
        Row(
            Modifier
               /* .clickable {

                }*/,
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

            Text(
                text = "$ ${game.bestPrice}",
                modifier = Modifier,
                style = MaterialTheme.typography.titleMedium
            )

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
            Column {

                for ((index, deal) in game.deals.withIndex()) {

                    DealRow(deal = deal, onDealPressed = onDealPressed)

                    if (index != game.deals.lastIndex) {

                        Divider(thickness = 1.dp)
                    }
                }
            }

        }
    }
}



