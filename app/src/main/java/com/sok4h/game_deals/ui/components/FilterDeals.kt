package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sok4h.game_deals.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDeals(
    sortvalueIndex: Int,
    minPrice: String,
    maxPrice: String,
    onSortChanged: (sortValue: String, sortID: Int) -> Unit,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onFilterChanged: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(8.dp)
            .testTag("filter dialog")
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.deals_filters),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = stringResource(id = R.string.filter_by),
            style = MaterialTheme.typography.titleMedium
        )

        val radioOptions = stringArrayResource(id = R.array.filter_list)


        var expanded by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {

            OutlinedTextField(modifier = Modifier.testTag("dropdown")
                .fillMaxWidth()
                .menuAnchor(),
                value = stringArrayResource(id = R.array.filter_list)[sortvalueIndex],
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
                radioOptions.forEachIndexed { index, option ->

                    DropdownMenuItem(text = { Text(text = option) }, onClick = {





                        onSortChanged(
                            context.resources.getStringArray(R.array.filter_list_eng)[index],
                            index
                        )


                        expanded = false
                    })

                    Divider()
                }

            }

        }

        Text(
            text = stringResource(id = R.string.deals_filter_price_range),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {

            OutlinedTextField(modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = stringResource(R.string.min_price)) },
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
                label = { Text(text = stringResource(R.string.max_price)) },
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
                Text(text = stringResource(id = R.string.update_filters))
            }

        }

    }


}
