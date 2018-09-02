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
import android.support.v4.app.ActivityCompat
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.content.IntentFilter




class MainActivity : AppCompatActivity() , AnkoLogger{

    companion object {
        val MESSAGE_KEY = "MESSAGE:KEY"
        val intentname = "ACTION_PROXIMITY_ALERT"
    }

    val RADIUS : Float = 100f // 10 meters
    val EXPIRATION : Long = 6000 // one hour
    val EXPIRATION_ONE_DAY : Long = 86400000 // one day
    val INTENT_REQUEST_CODE : Int = 1

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
        placesBox.put(Place(0,latitude,longitude,message))
        var myintent = Intent(intentname)
        myintent.putExtra(MESSAGE_KEY,message)
        var mypendingInent = PendingIntent.getBroadcast(applicationContext,INTENT_REQUEST_CODE,myintent,PendingIntent.FLAG_CANCEL_CURRENT)
        tesztGpsPermission()
        locationManager.addProximityAlert(latitude,longitude,RADIUS,EXPIRATION_ONE_DAY,mypendingInent)

        val filter = IntentFilter(intentname)

        this.registerReceiver(ProximityAlertReceiver(), filter)

        var placeList = placesBox.all
        for (placeitem in placeList) {
            info { " Saved places: latitude ${placeitem.latitude} longitude: ${placeitem.longitude} message: ${placeitem.message} "}
        }


    }

    private fun tesztGpsPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            info( "No GPS access")
        } else {
            info( "Access GPS already granted")
        }

    }
}
