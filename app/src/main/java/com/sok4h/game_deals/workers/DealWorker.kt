package com.sok4h.game_deals.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sok4h.game_deals.MainActivity
import com.sok4h.game_deals.R
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.repositories.IGamesRepository
import com.sok4h.game_deals.ui.ui_model.mappers.toDealModel
import com.sok4h.game_deals.ui.ui_model.mappers.toGameDetailModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DealWorker(var ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params), KoinComponent {

    private val gamesRepository: IGamesRepository by inject()
    override suspend fun doWork(): Result {
        val games = gamesRepository.getListGamesfromDatabase()

        games.forEach { game ->

            val result = gamesRepository.getGameById(game.gameId)

            if (result.isSuccess) {

                val updatedGame = result.getOrNull()

                if (updatedGame != null) {
                    if (game.bestDealId.contentEquals(updatedGame.deals[0].dealID)) {

                        return@forEach

                    } else {

                        gamesRepository.addGametoWatchList(
                            updatedGame.toGameDetailModel(
                                game.gameId,
                                true,
                                updatedGame.deals.map {
                                    it.toDealModel(
                                        gamesRepository.getStorefromDatabase(
                                            it.storeID
                                        ).StoreName
                                    )
                                })
                        )
                        makeNotification(context = ctx, updatedGame)
                    }

                }
            }

        }


        return Result.success()
    }

}


fun makeNotification(context: Context, game: GameDetailDto) {


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.deal_channel_name)
        val description = context.getString(R.string.deal_notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Deals", name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    val notificationIntent = Intent(context, MainActivity::class.java)
    notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val notificationDescription= context.resources.getString(R.string.deal_notification_description,game.info.title,game.deals[0].price)
    val builder = NotificationCompat.Builder(context, "Deals")
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.baseline_trending_down_24
            )
        )
        .setContentTitle(context.getString(R.string.deal_notification_title))
        .setContentText(notificationDescription)
        .setPriority(NotificationCompat.PRIORITY_HIGH)


    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    val oneTimeID = (SystemClock.uptimeMillis() % 99999999)
    NotificationManagerCompat.from(context).notify(oneTimeID.toInt(), builder.build())
}

