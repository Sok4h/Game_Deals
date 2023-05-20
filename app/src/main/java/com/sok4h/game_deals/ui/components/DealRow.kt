package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.ui_model.DealModel

@Composable
fun DealRow(deal: DealModel) {

    val uriHandler = LocalUriHandler.current
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = deal.storeImage, contentDescription = "", modifier = Modifier.size(30.dp)
        )

        Text(text = deal.storeName!!, Modifier.weight(1.5f))

        Column(Modifier.weight(1f)) {

            Text(text = "$" + deal.price, modifier = Modifier.fillMaxWidth())

            if (!deal.price.contentEquals(deal.retailPrice)) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$" + deal.retailPrice,
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough, color = Color.Red
                    ),
                    fontSize = 12.sp
                )

            }
        }

        TextButton(onClick = { uriHandler.openUri("https://www.cheapshark.com/redirect?dealID=${deal.dealID}") }) {

            Text(text = stringResource(id = R.string.buy))
        }

    }

}