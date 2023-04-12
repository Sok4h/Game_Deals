package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.ui.theme.Game_DealsTheme


@Composable
fun FilterDealsBottomSheet() {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()


    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Filters",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(text = "Sort by", style = MaterialTheme.typography.titleMedium)

        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .selectableGroup()
                .fillMaxWidth()
        ) {


            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = false, onClick = { /*TODO*/ })
                Text(text = "Recent")
            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = true, onClick = { /*TODO*/ })
                Text(text = "Savings")
            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = false, onClick = { /*TODO*/ })
                Text(text = "Store")
            }

        }


        Divider(thickness = 1.dp)

        // TODO: Preguntar a la nasa como carajos le pongo el label
/*
                    Text(text = "Price range", style = MaterialTheme.typography.titleMedium)

                    var sliderState by remember { mutableStateOf(0f..50f) }

                    RangeSlider(
                        modifier = Modifier,
                        value = sliderState,
                        onValueChange = { },
                        valueRange = 0f..50f,
                        steps = 0,
                      )*/

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Min") },
                value = "",
                placeholder = { Text(text = "0") },
                onValueChange = {},
                prefix = { Text(text = "$") })

            OutlinedTextField(modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Max") },
                value = "",
                placeholder = { Text(text = "50") },
                onValueChange = {},
                prefix = { Text(text = "$") })
        }

        Divider(thickness = 1.dp)


        Row(Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {

            Button(onClick = { /*TODO*/ },) {
                Text(text = "Save Preferences")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Reset filters")
            }

        }


    }


}

@Preview( )
@Composable
fun Test() {

    Game_DealsTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){

        FilterDealsBottomSheet()

        }
    }
}
