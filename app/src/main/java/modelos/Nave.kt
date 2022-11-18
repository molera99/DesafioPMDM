package modelos

import android.graphics.Bitmap
import java.io.Serializable

class Nave(var matricula:String,var tipo:String,var carga:Boolean,var pasajeros:Boolean,var foto:String): Serializable {
}