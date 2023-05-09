@file:OptIn(ExperimentalMaterial3Api::class)

package com.sok4h.game_deals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.viewStates.MainScreenState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WatchListScreen(
    state: MainScreenState,
    onRemoveFromWatchList: (String) -> Unit,

    ) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val openDialog = remember { mutableStateOf(false) }
        val notificationPermissionState = rememberPermissionState(
            android.Manifest.permission.POST_NOTIFICATIONS
        )

        if (!notificationPermissionState.status.isGranted) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Enable notication to never miss a deal!")

                OutlinedButton(onClick = {

                    if (notificationPermissionState.status.shouldShowRationale) {
                        openDialog.value = true

                    } else {
                        notificationPermissionState.launchPermissionRequest()
                    }

                }) {
                    Text(text = "Give permission")
                }
            }
        }


        if (openDialog.value) {

            AlertDialog(onDismissRequest = { openDialog.value = false }) {
                Surface(shape = MaterialTheme.shapes.large) {
                    Column(
                        Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationAdd,
                            contentDescription = "Icon"
                        )
                        Text(
                            text = "Never miss a new deal",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(text = "We need your permission to notify you every time there´s a new offer for your favorite game")
                        Button(onClick = {
                            notificationPermissionState.launchPermissionRequest()
                            openDialog.value = false
                        }) {
                            Text(text = "Accept permission")
                        }

                    }
                }
            }
        }

        if (state.isWatchlistLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(25.dp),
                strokeWidth = 2.dp
            )

        }

        if (state.watchListState.isNotEmpty()) {

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp),
                text = "Favorite Games",
                style = MaterialTheme.typography.titleLarge,
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
            ) {

                items(items = state.watchListState) { game ->

                    GameDealCard(
                        game = game,
                        onAddToWatchList = {},
                        onRemoveFromWatchList = {
                            onRemoveFromWatchList(it)
                        }
                    )
                }

            }

        }

        if (state.watchListState.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No tienes juegos en favoritos",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }

        }

    }


// TODO: add error case


}