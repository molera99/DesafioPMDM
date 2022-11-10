package auxiliar

object Conexion {
    var nombreBD = "administracion.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }
}