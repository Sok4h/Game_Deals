package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sok4h.game_deals.ui.ui_model.DealModel

@Composable
fun DealRow(deal: DealModel, onDealPressed: (String) -> Unit) {

    // TODO: revisar el caso donde la oferta sea el precio por defecto (no poner las letras rojas) 
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = deal.storeImage,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )

        Text(text = deal.storeName!!, Modifier.weight(1.5f))

        Column(Modifier.weight(1f)) {

            Text(text = "$" + deal.price, modifier = Modifier.fillMaxWidth())
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "$" + deal.retailPrice,
                style = TextStyle(textDecoration = TextDecoration.LineThrough, color = Color.Red),
                fontSize = 12.sp
            )
        }

        TextButton(onClick = { onDealPressed(deal.dealID) }) {

            Text(text = "Comprar")
        }

    }

}