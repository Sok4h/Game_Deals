package com.sok4h.game_deals.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@ExperimentalMaterial3Api
@Composable
fun SearchBar(
    modifier: Modifier,
    textValue: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
) {

    Row(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textValue,
            singleLine = true,
            label = { Text(text = "Search Game") },
            onValueChange = onQueryChanged,
            trailingIcon = {
                IconButton(onClick = onSearch) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Game")
                }

            },
            keyboardActions = KeyboardActions(

                onSearch = { onSearch() },

                ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }

}