@file:OptIn(ExperimentalMaterial3Api::class)

package com.sok4h.game_deals.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.judemanutd.autostarter.AutoStartPermissionHelper
import com.sok4h.game_deals.R
import com.sok4h.game_deals.ui.components.GameDealCard
import com.sok4h.game_deals.ui.components.NotificationDialog
import com.sok4h.game_deals.ui.viewStates.MainScreenState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WatchListScreen(
    state: MainScreenState,
    onRemoveFromWatchList: (String) -> Unit,
    onAutoStartShown: () -> Unit

) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var openDialog by remember { mutableStateOf(false) }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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

                    Text(text = stringResource(id = R.string.enable_notifications))

                    OutlinedButton(onClick = {

                        if (notificationPermissionState.status.shouldShowRationale) {
                            openDialog = true

                        } else {
                            notificationPermissionState.launchPermissionRequest()
                        }

                    }) {
                        Text(text = stringResource(id = R.string.give_permission))
                    }
                }
            }

            if (openDialog) {
                NotificationDialog(
                    onDismiss = { openDialog = false },
                    onAccept = {
                        notificationPermissionState.launchPermissionRequest().also {
                            val isActive = AutoStartPermissionHelper.getInstance()
                                .isAutoStartPermissionAvailable(context)
                            if (isActive) {
                                AutoStartPermissionHelper.getInstance()
                                    .getAutoStartPermission(context, true)
                            }
                        }
                        openDialog = false
                    })
            }
        } else {

            val isActive =
                AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(context)

            if (isActive && !state.autoStartHasBeenShown) {
                Text(text = stringResource(id = R.string.auto_start_description))
                OutlinedButton(onClick = {
                    AutoStartPermissionHelper.getInstance()
                        .getAutoStartPermission(context, true)
                    onAutoStartShown()

                }) {
                    Text(text = stringResource(id = R.string.give_permission))
                }
            }
        }

        if (state.isWatchlistLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp), strokeWidth = 2.dp
            )
        }
        if (state.watchListState.isNotEmpty()) {

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp),
                text = stringResource(R.string.favorite_games),
                style = MaterialTheme.typography.titleLarge,
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
            ) {
                items(items = state.watchListState) { game ->

                    GameDealCard(game = game, onAddToWatchList = {}, onRemoveFromWatchList = {
                        onRemoveFromWatchList(it)
                    })
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
                    text = stringResource(R.string.no_tienes_juegos_en_favoritos),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }


    if (state.gameListError.isNotEmpty()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = state.gameListError)
        }
    }
}