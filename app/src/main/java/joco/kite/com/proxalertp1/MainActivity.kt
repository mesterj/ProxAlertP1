package joco.kite.com.proxalertp1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import android.provider.ContactsContract.CommonDataKinds.Note
import io.objectbox.Box



class MainActivity : AppCompatActivity() , AnkoLogger{

    lateinit var boxStore : MyObjectBox
    lateinit var placesBox : Box<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // do this once, for example in your Application class notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);
        var boxStore= MyObjectBox.builder().androidContext(this).build()
        // do this in your activities/fragments to get hold of a Box
        placesBox = boxStore.boxFor(Place::class.java)
        
    }
}
