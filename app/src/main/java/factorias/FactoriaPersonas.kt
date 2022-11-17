package factorias


import modelos.Piloto
import modelos.Vader



object FactoriaPersonas {

    fun crearVader():Vader{
        var nombre="Vader"
        var edad=45
        var contraseña="12345"
        var v=Vader(nombre,edad, contraseña)
        return v
    }

    fun crearPiloto(nombre:String,edad:Int,contraseña:String,experiencia:Int,foto:String):Piloto{
        var piloto=Piloto(nombre,edad,contraseña,experiencia,foto)
        return piloto
    }
}