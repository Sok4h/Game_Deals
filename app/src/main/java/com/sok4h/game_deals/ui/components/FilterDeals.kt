package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDeals(
    sortValue: String,
    minPrice: String,
    maxPrice: String,
    onSortChanged: (String) -> Unit,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onFilterChanged: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .testTag("filter dialog")
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

        val radioOptions = listOf("Deal Rating","Recent", "Savings", "Store", "Price", "Title","Reviews")

        var expanded by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {

            OutlinedTextField(modifier = Modifier.testTag("dropdown")
                .fillMaxWidth()
                .menuAnchor(),
                value = sortValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (!expanded) {
                            Icons.Default.ArrowDropDown
                        } else {

                            Icons.Default.ArrowDropUp
                        }, contentDescription = ""
                    )
                })

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                radioOptions.forEach { option ->

                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {

            OutlinedTextField(modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Min") },
                value = minPrice,
                placeholder = {
                    Text(
                        text = "0",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                },

                onValueChange = {
                    onMinPriceChanged(it)
                },
                prefix = { Text(text = "$") })

            OutlinedTextField(modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Max") },
                value = maxPrice,
                placeholder = {
                    Text(
                        text = "50",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                },
                onValueChange = {

                    onMaxPriceChanged(it)

                },
                prefix = { Text(text = "$") })
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = {
                onFilterChanged()

                expanded = false
            }) {
                Text(text = "Update filters")
            }/*  TextButton(onClick = { }) {
                Text(text = "Reset filters")
            }*/

        }

    }


}
