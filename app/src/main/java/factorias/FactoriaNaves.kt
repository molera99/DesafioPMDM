package factorias

import modelos.Nave

object FactoriaNaves {

    fun crearNave(matricula:String,tipo:String,carga:Boolean,pasajeros:Boolean,foto:String): Nave {
        var nave=Nave(matricula, tipo, carga, pasajeros, foto)
        return nave
    }
}