package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.components.DealCard
import com.sok4h.game_deals.ui.components.FilterDeals
import com.sok4h.game_deals.ui.viewStates.MainScreenState
import com.sok4h.game_deals.util.DEALSPAGESIZE

@ExperimentalMaterial3Api
@Composable
fun DealScreen(
    state: MainScreenState,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onSortChanged: (sort: String, id: Int) -> Unit,
    onFilterChanged: () -> Unit,
    onScrollChanged: (Int) -> Unit,
    onChangePage: () -> Unit
) {

    var openFilterDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.explore_deals),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = { openFilterDialog = !openFilterDialog },

                ) {
                Icon(imageVector = Icons.Default.FilterList, contentDescription = "filter deals")
            }
        }
        if (state.dealListState.isNotEmpty()) {

            Box(modifier = Modifier.fillMaxSize()) {

                LazyVerticalGrid(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(2),
                    content = {

                        itemsIndexed(items = state.dealListState) { index, deal ->

                            onScrollChanged(index)

                            if (index + 1 >= (state.dealPageNumber + 1) * DEALSPAGESIZE && !state.isLoading) {
                                onChangePage()
                            }
                            DealCard(
                                deal = deal,
                                modifier = Modifier.wrapContentWidth()
                            )

                        }
                    },

                    )
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                }

            }
        }



        if (openFilterDialog) {

            Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = { openFilterDialog = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.medium,
                    /*color = MaterialTheme.colorScheme.onBackground*/
                ) {
                    FilterDeals(
                        sortvalueIndex = state.sortOptionIndex,
                        minPrice = state.minPrice,
                        maxPrice = state.maxPrice,
                        onSortChanged = onSortChanged,
                        onMinPriceChanged = onMinPriceChanged,
                        onMaxPriceChanged = onMaxPriceChanged,
                    ) {
                        onFilterChanged()
                        openFilterDialog = false
                    }

                }
            }
        }

        if (state.isLoading && state.dealListState.isEmpty()) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                CircularProgressIndicator(
                    modifier = Modifier.size(25.dp), strokeWidth = 2.dp,
                )
            }
        }

        if (state.dealListErrorMessage.isNotEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {

                val error: String = when (state.dealListErrorMessage) {

                    "400" -> stringResource(id = R.string.error_400)
                    "404" -> stringResource(id = R.string.error_404)
                    "500" -> stringResource(id = R.string.error_500)
                    "429" -> stringResource(id = R.string.error_429)
                    else -> {
                        stringResource(id = R.string.generic_error)
                    }
                }

                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "error",
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = error,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Button(onClick = { onFilterChanged() }) {

                    Text(text = stringResource(R.string.try_again))
                }
            }
        }
    }
}

