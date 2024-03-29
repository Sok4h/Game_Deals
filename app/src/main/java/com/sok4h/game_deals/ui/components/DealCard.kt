package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.ui_model.DealDetailModel

@Composable
fun DealCard(modifier: Modifier, deal: DealDetailModel) {

    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${deal.dealID}")
            },
    ) {

        AsyncImage(
            model = deal.gameImage, modifier = Modifier
                .fillMaxHeight(0.4f),

            contentDescription = "", contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = deal.title,
                modifier = Modifier,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = deal.storeLogo, contentDescription = "", modifier = Modifier.size(16.dp)
                )
                Text(text = "$" + deal.salePrice, modifier = Modifier)

                if (!deal.salePrice.contentEquals(deal.normalPrice)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$" + deal.normalPrice,
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough, color = Color.Red
                        ),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DealCardPreview() {

    DealCard(
        modifier = Modifier.height(200.dp), deal = DealDetailModel(
            dealID = "Kathrina",
            dealRating = "Rayan",
            gameID = "Malina",
            isOnSale = "Dejuan",
            lastChange = 8300,
            normalPrice = "Ciara",
            salePrice = "Sharyn",
            savings = "Christipher",
            numberReviews = "Vu",
            steamRatingPercent = null,
            steamRatingText = null,
            storeID = "Timohty",
            gameImage = "Jacquelynne",
            title = "Rosalia texto de dos lineas seguramente",
            storeLogo = "Shanna"
        )
    )
}
