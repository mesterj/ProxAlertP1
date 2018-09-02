package joco.kite.com.proxalertp1

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import android.provider.ContactsContract.CommonDataKinds.Note
import android.support.v4.app.ActivityCompat
import android.util.Log
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() , AnkoLogger{

    companion object {
        val MESSAGE_KEY = "MESSAGE:KEY"
    }

    val RADIUS : Float = 1000f
    val EXPIRATION : Long = -1
    val INTENT_REQUEST_CODE : Int = 1
    val intentname : String = "joco.kite.com.proxalertp1.showMessage_activity"

    lateinit var boxStore : MyObjectBox
    lateinit var placesBox : Box<Place>
    lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // do this once, for example in your Application class notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);
        var boxStore= MyObjectBox.builder().androidContext(this).build()
        // do this in your activities/fragments to get hold of a Box
        placesBox = boxStore.boxFor(Place::class.java)
        tesztGpsPermission()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        btnSave.onClick { setAlert(etLatValue.text.toString().toDouble(),etLongValue.text.toString().toDouble(),etMessage.text.toString()) }
    }

    @SuppressLint("MissingPermission")
    private fun setAlert(latitude: Double, longitude: Double, message: String) {
        var myintent = Intent(intentname)
        myintent.putExtra(MESSAGE_KEY,message)
        var mypendingInent = PendingIntent.getBroadcast(applicationContext,INTENT_REQUEST_CODE,myintent,PendingIntent.FLAG_CANCEL_CURRENT)
        tesztGpsPermission()
        locationManager.addProximityAlert(latitude,longitude,RADIUS,EXPIRATION,mypendingInent)


    }

    private fun tesztGpsPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            info( "Nem volt jog a GPS használatához")
        } else {
            info( "Már volt jog a GPS koordináták olvasáshoz")
        }

    }
}
