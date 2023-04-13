package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDeals(sortValue: String, onSortChanged: (String) -> Unit) {

    // TODO: Añadir botón cerrar con su respectivo estado 
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Filters",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(text = "Search by", style = MaterialTheme.typography.titleMedium)

        val radioOptions = listOf("Recent", "Savings", "Store", "Price", "Title")

        var expanded by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), value = sortValue, onValueChange = {}, readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (!expanded) {
                            Icons.Default.ArrowDropDown
                        } else {

                            Icons.Default.ArrowDropUp
                        },
                        contentDescription = ""
                    )
                }
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                radioOptions.forEach { option ->

                    DropdownMenuItem(text = { Text(text = option) },
                        onClick = {
                            onSortChanged(option)
                            expanded = false
                        })

                    Divider()
                }

            }

        }


        // TODO: Preguntar a la nasa como carajos le pongo el label
/*


                    var sliderState by remember { mutableStateOf(0f..50f) }

                    RangeSlider(
                        modifier = Modifier,
                        value = sliderState,
                        onValueChange = { },
                        valueRange = 0f..50f,
                        steps = 0,
                      )*/

        Text(text = "Price range", style = MaterialTheme.typography.titleMedium)


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {

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




        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Update filters")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Reset filters")
            }

        }

    }


}
