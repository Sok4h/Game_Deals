package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.ui_model.DealDetailModel

@Composable
fun DealCard(deal: DealDetailModel, onDealPressed: (String) -> Unit) {

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp)
            .wrapContentHeight()

            .clickable { onDealPressed(deal.dealID) },

        ) {

            AsyncImage(
                model = deal.gameImage,
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),

                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {



            Text(
                text = deal.title,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = deal.storeLogo,
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Text(text = "$" + deal.salePrice, modifier = Modifier)
            }


        }
    }
}

@Preview
@Composable
fun megaxd() {
    LazyColumn() {
        items(count = 4) {
            DealCardPreview()
        }
    }
}

@Composable
fun DealCardPreview() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(16.dp)

    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),

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
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Borderlands 3",
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(text = "$" + "350", modifier = Modifier)
        }


    }
}