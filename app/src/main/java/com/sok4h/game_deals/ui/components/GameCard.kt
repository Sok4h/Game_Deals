package com.sok4h.game_deals.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.ui_model.GameDetailModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDealCard(
    game: GameDetailModel,
    onAddToWatchList: (GameDetailModel) -> Unit,
    onRemoveFromWatchList: (String) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Row(
            Modifier,
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
                    text = game.info.title ?: stringResource(R.string.game_title_no_available),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(id = R.string.deals_quantity, game.deals.size),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "$ ${game.bestPrice}",
                modifier = Modifier.padding(4.dp),
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
                        imageVector = Icons.Filled.Favorite,
                        tint = Color.Red,
                        contentDescription = "remove from favorite"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "save to favorites"
                    )

                }
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column {
                for ((index, deal) in game.deals.withIndex()) {

                    DealRow(deal = deal)

                    if (index != game.deals.lastIndex) {

                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}



