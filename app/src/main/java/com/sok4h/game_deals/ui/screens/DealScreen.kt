package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sok4h.game_deals.ui.components.DealCard
import com.sok4h.game_deals.ui.components.FilterDeals
import com.sok4h.game_deals.ui.viewStates.MainScreenState

@ExperimentalMaterial3Api
@Composable
fun DealScreen(
    state: MainScreenState,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
    onSortChanged: (String) -> Unit,
    onFilterChanged: () -> Unit,
) {

    // TODO: Hoistear estado clickear deal
    var openFilterDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {


        if (state.dealListState.isNotEmpty()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Explore Deals", style = MaterialTheme.typography.titleLarge)
                IconButton(
                    onClick = { openFilterDialog = !openFilterDialog },

                    ) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = "")
                }
            }


            LazyVerticalGrid(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
                content = {

                    items(items = state.dealListState) { deal ->

                        DealCard(
                            deal = deal,
                            onDealPressed = { /*onDealPressed(it)*/ },
                            modifier = Modifier.wrapContentWidth()
                        )

                    }
                },

                )
            if (openFilterDialog) {

                AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
                    onDismissRequest = { openFilterDialog = false }) {

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentHeight(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        FilterDeals(
                            sortValue = state.sortDealsBy,
                            onSortChanged = onSortChanged,
                            minPrice = state.minPrice,
                            maxPrice = state.maxPrice,
                            onMaxPriceChanged = onMaxPriceChanged,
                            onMinPriceChanged = onMinPriceChanged,
                            onFilterChanged = {
                                onFilterChanged()
                                openFilterDialog = false
                            },
                        )

                    }
                }
            }
        }
    }
}
