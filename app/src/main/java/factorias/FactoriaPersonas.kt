package factorias

import modelos.Vader

object FactoriaPersonas {

    fun crearVader():Vader{
        var nombre="Vader"
        var edad=45
        var contraseña="12345"
        var v=Vader(nombre,edad, contraseña)
        return v
    }
}