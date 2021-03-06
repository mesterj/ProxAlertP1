package joco.kite.com.proxalertp1

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import joco.kite.com.proxalertp1.MainActivity.Companion.MESSAGE_KEY
import joco.kite.com.proxalertp1.MainActivity.Companion.intentname
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.longToast

class ProximityAlertReceiver : BroadcastReceiver() , AnkoLogger{

    override fun onReceive(context: Context, intent: Intent) {
        var key = LocationManager.KEY_PROXIMITY_ENTERING
        var infostring = ""
        var entering = intent.getBooleanExtra(key, false);

               if (entering) {
                   debug {" $javaClass.simpleName entering" }
                   infostring = "entering to area"
               }
        else {
            debug(" $javaClass.simpleName exiting");
            infostring = "exiting from area"
        }

        var message = intent.extras.getString(MESSAGE_KEY,"" )
        var notificationmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        info(message+" "+infostring)
       // context.longToast("Show in toast : $message $infostring")
        var notifintent = Intent(intentname).putExtra(MESSAGE_KEY,message+" "+ infostring)
        var notifPendingintent = PendingIntent.getBroadcast(context,0,notifintent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notibuilder=   NotificationCompat.Builder(context,"default")
                .setContentText(message+ " " + infostring)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(notifPendingintent)

        notificationmanager.notify(0,notibuilder.build())
    }
}
