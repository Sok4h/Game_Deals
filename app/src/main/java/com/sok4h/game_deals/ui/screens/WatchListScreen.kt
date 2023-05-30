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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import com.sok4h.game_deals.ui.viewStates.MainScreenState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WatchListScreen(
    state: MainScreenState,
    onRemoveFromWatchList: (String) -> Unit,
    fetchGamesfromDatabase: () -> Unit,
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val openDialog = remember { mutableStateOf(false) }

        if (state.watchListErrorMessage.isEmpty()) {

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
                                openDialog.value = true

                            } else {
                                notificationPermissionState.launchPermissionRequest()
                            }

                        }) {
                            Text(text = stringResource(id = R.string.give_permission))
                        }
                    }
                }


                if (openDialog.value) {

                    AlertDialog(onDismissRequest = { openDialog.value = false }) {
                        Surface(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.large,
                            tonalElevation = AlertDialogDefaults.TonalElevation
                        ) {
                            Column(
                                Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationAdd,
                                    contentDescription = "Icon"
                                )
                                Text(
                                    text = stringResource(id = R.string.permission_dialog_title),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(text = stringResource(id = R.string.permission_dialog_content))

                                Button(onClick = {
                                    notificationPermissionState.launchPermissionRequest().also {

                                        val isActive = AutoStartPermissionHelper.getInstance()
                                            .isAutoStartPermissionAvailable(context)

                                        if (isActive) {

                                            AutoStartPermissionHelper.getInstance()
                                                .getAutoStartPermission(context, true)
                                        }
                                    }

                                    openDialog.value = false
                                }) {
                                    Text(text = stringResource(id = R.string.give_permission))
                                }


                            }
                        }
                    }
                }
            } else {

                val isActive =
                    AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(context)

                // TODO: save in datastore confirmation that the autostart message has been shown
                if (isActive) {

                    Text(text = stringResource(id = R.string.auto_start_description))

                    OutlinedButton(onClick = {
                        AutoStartPermissionHelper.getInstance()
                            .getAutoStartPermission(context, true)

                    }) {
                        Text(text = stringResource(id = R.string.give_permission))
                    }

                }


            }
        }

        if (state.isWatchlistLoading) {
            Column(verticalArrangement = Arrangement.Center) {


                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp)
                        .size(25.dp)
                        .testTag("loading watchlist"),
                    strokeWidth = 2.dp
                )
            }

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

        if (state.watchListState.isEmpty() && state.watchListErrorMessage.isEmpty()) {

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
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .testTag("No games Text")
                )

            }

        }




        if (state.watchListErrorMessage.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                val error: String = when (state.watchListErrorMessage) {

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
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Button(onClick = { fetchGamesfromDatabase() }) {

                    Text(text = stringResource(R.string.try_again))
                }
            }
        }

    }


}
