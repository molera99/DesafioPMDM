package modelos

import android.graphics.Bitmap
import java.io.Serializable
import kotlin.random.Random

class Nave(var matricula:String,var tipo:String,var carga:Boolean,var pasajeros:Boolean,var foto:String): Serializable {

    fun combateCaza(piloto: Piloto): Boolean {
        var probabilidad: Int
        var resultado = false
        if (piloto.experiencia in 1..50) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..3) {
                resultado = true
            }
        } else if (piloto.experiencia in 51..100) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..5) {
                resultado = true
            }
        }else{
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..8) {
                resultado = true
            }
        }
        return resultado
    }

    fun combateBombardeo(piloto: Piloto):Boolean{
        var probabilidad: Int
        var resultado = false
        if (piloto.experiencia in 1..50) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..3) {
                resultado = true
            }
        } else if (piloto.experiencia in 51..100) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..5) {
                resultado = true
            }
        }else{
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..8) {
                resultado = true
            }
        }
        return resultado
    }

    fun tormentaSolar(piloto: Piloto):Boolean{
        var probabilidad: Int
        var resultado = false
        if (piloto.experiencia in 1..50) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..5) {
                resultado = true
            }
        } else if (piloto.experiencia in 51..100) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..7) {
                resultado = true
            }
        }else{
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..9) {
                resultado = true
            }
        }
        return resultado
    }

    fun ataqueAereo(piloto: Piloto):Boolean{
        var probabilidad: Int
        var resultado = false
        if (piloto.experiencia in 1..50) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..4) {
                resultado = true
            }
        } else if (piloto.experiencia in 51..100) {
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..6) {
                resultado = true
            }
        }else{
            probabilidad = Random.nextInt(1, 11)
            if (probabilidad in 1..8) {
                resultado = true
            }
        }
        return resultado
    }
}
