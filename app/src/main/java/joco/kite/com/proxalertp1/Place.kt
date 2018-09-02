package joco.kite.com.proxalertp1

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Place(
        @Id
        var id :Long  = 0,
        var latitude : Double = 0.1,
        var longitude: Double  = 0.1,
        var message : String = " "
)
