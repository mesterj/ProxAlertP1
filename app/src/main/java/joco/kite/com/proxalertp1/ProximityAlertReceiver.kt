package joco.kite.com.proxalertp1

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import joco.kite.com.proxalertp1.MainActivity.Companion.MESSAGE_KEY
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ProximityAlertReceiver : BroadcastReceiver() , AnkoLogger{

    override fun onReceive(context: Context, intent: Intent) {
        var message = intent.extras.getString(MESSAGE_KEY,"" )
        var notificationmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        info(message)
        var notifintent = Intent(context,showMessage_activity::class.java).putExtra(MESSAGE_KEY,message)
        var notifPendingintent = PendingIntent.getActivity(context,0,notifintent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notibuilder=   NotificationCompat.Builder(context,"default")
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(notifPendingintent)

        notificationmanager.notify(0,notibuilder.build())
    }
}
