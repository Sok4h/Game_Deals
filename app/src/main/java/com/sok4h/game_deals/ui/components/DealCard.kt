package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.ui_model.DealDetailModel

@Composable
fun DealCard(deal: DealDetailModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(16.dp)



    ) {

        AsyncImage(
            model = deal.gameImage,
            modifier = Modifier
                .height(80.dp),

            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically

            ) {
            AsyncImage(
                model = deal.storeLogo,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Text(text = deal.title, modifier = Modifier.weight(1f), overflow = TextOverflow.Ellipsis,maxLines = 2)
            Text(text = "$" + deal.salePrice, modifier = Modifier)
        }


    }
}