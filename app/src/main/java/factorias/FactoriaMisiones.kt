package factorias

import auxiliar.ContadorId
import modelos.Bombardeo
import modelos.Combate
import modelos.Mision
import modelos.Vuelo

object FactoriaMisiones {

    fun crearVuelo(duracion:Int):Vuelo{
        ContadorId.idMision++
        var id=ContadorId.idMision
        var tipo="Vuelo"
        var nombre=null
        var matricula=null
        var resultado=null
        var mision=Vuelo(id,tipo,nombre,matricula,resultado,duracion)
        return mision
    }
    fun crearBombardeo(numBombarderos:Int):Bombardeo{
        ContadorId.idMision++
        var id=ContadorId.idMision
        var tipo="Bombardeo"
        var nombre=null
        var matricula=null
        var resultado=null
        var mision=Bombardeo(id,tipo,nombre,matricula,resultado,numBombarderos)
        return mision
    }
    fun crearCombate(numCazas:Int):Combate{
        ContadorId.idMision++
        var id=ContadorId.idMision
        var tipo="Combate"
        var nombre=null
        var matricula=null
        var resultado=null
        var mision=Combate(id,tipo,nombre,matricula,resultado,numCazas)
        return mision
    }
}