package com.sok4h.game_deals.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sok4h.game_deals.R
import com.sok4h.game_deals.data.model.dtos.GameDetailDto
import com.sok4h.game_deals.data.repositories.IGamesRepository
import kotlinx.coroutines.flow.collectLatest

class DealWorker(var ctx: Context, params: WorkerParameters, val gamesRepository: IGamesRepository) :
    CoroutineWorker(ctx, params) {


    override suspend fun doWork(): Result {

        gamesRepository.getGamesfromDatabase().collectLatest { games ->


            games.forEach { game ->

                val result = gamesRepository.getGameById(game.gameId)

                if (result.isSuccess) {

                    val updatedGame = result.getOrNull()


                    if (updatedGame != null) {
                        if (game.bestDealId.contentEquals(updatedGame.deals[0].dealID)) {

                            return@forEach

                        } else {

                            makeNotification(context = ctx,updatedGame)
                        }

                    }
                }

            }


        }

       return Result.success()
    }

}


fun makeNotification(context:Context,game:GameDetailDto){

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Deal Updates"
        val description = "Keeps you up to date with the latest deals for your favorite games"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Deals", name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, "Deals")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("New Deal found ")
        .setContentText("your game ${game.info.title} price has drop to ${game.deals[0].price}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    NotificationManagerCompat.from(context).notify(1, builder.build())
}
