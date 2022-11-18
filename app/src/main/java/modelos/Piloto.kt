package modelos

class Piloto(nombre: String, edad: Int, contraseña: String,var experiencia: Int,var foto: String) : Persona(nombre, edad, contraseña) {


    fun sumarExperienciaCombate(num:Int){
        experiencia += (num * 10)
    }
    fun sumaExperienciaBombardero(num:Int,nave: Nave){
        experiencia += (num * 5)
        if (nave.carga){
            experiencia += 5
        }
        if (nave.pasajeros){
            experiencia += 10
        }
    }
    fun sumarExperienciaVuelo(nave: Nave){
        experiencia+=10
        if (nave.carga){
            experiencia += 5
        }
        if (nave.pasajeros){
            experiencia += 10
        }
    }
}