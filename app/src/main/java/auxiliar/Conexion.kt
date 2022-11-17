package auxiliar

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import conexion.AdminSQLIteConexion
import modelos.*

object Conexion {
    var nombreBD = "administracion.db3"

    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addVader(contexto: AppCompatActivity, v: Vader){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", v.nombre)
        registro.put("dis", "Admin")
        registro.put("edad",v.edad)
        registro.put("contraseña", v.contraseña)
        bd.insert("persona", null, registro)
        bd.close()
    }
    fun addPiloto(contexto: AppCompatActivity, p: Piloto){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", p.nombre)
        registro.put("dis", "User")
        registro.put("edad",p.edad)
        registro.put("contraseña", p.contraseña)
        registro.put("experiencia",p.experiencia)
        registro.put("foto",p.foto)
        bd.insert("persona", null, registro)
        bd.close()
    }

    fun addNave(contexto: AppCompatActivity, n: Nave){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        var carga=0
        var pasajeros=0
        if (n.carga){
            carga=1
        }
        if (n.pasajeros){
            pasajeros=1
        }
        registro.put("matricula", n.matricula)
        registro.put("tipo",n.tipo)
        registro.put("carga", carga)
        registro.put("pasajeros",pasajeros)
        registro.put("foto",n.foto)
        bd.insert("nave", null, registro)
        bd.close()
    }

    fun obtenerPersonas(contexto: AppCompatActivity){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var fila = bd.rawQuery("select nombre,edad,contraseña from persona where dis=\"Admin\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var p: Persona = Vader(fila.getString(0), fila.getInt(1), fila.getString(2))
                Listas.listaPersona.add(p)
            }
        }

        fila = bd.rawQuery("select nombre,edad,contraseña,experiencia,foto from persona where dis not like \"Admin\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var p: Persona = Piloto(fila.getString(0), fila.getInt(1), fila.getString(2), fila.getInt(3), fila.getString(4))
                Listas.listaPersona.add(p)
            }
        }
        bd.close()

    }

    fun obtenerNaves(contexto: AppCompatActivity){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var carga=false
        var pasajeros=false
        var fila = bd.rawQuery("select matricula,tipo,carga,pasajeros,foto from nave ", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                if(fila.getInt(2)==1){
                    carga=true
                }
                if (fila.getInt(3)==1){
                    pasajeros=true
                }
                var n: Nave = Nave(fila.getString(0), fila.getString(1),carga,pasajeros,fila.getString(4))
                Listas.listaNave.add(n)
            }
        }

        fila = bd.rawQuery("select nombre,edad,contraseña,experiencia,foto from persona where dis not like \"Admin\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var p: Persona = Piloto(fila.getString(0), fila.getInt(1), fila.getString(2), fila.getInt(3), fila.getString(4))
                Listas.listaPersona.add(p)
            }
        }
        bd.close()

    }

    fun comprobarVader(contexto:AppCompatActivity):Boolean{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var comprobar=true
        var fila = bd.rawQuery("select nombre,edad,contraseña from persona where dis=\"Admin\"", null)
        if (fila.count==0){
            comprobar=false
        }
        bd.close()
        return comprobar
    }

    fun comprobarCredenciales(contexto:AppCompatActivity,nombre:String,contraseña:String):Boolean{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var comprobar=false
        var fila = bd.rawQuery("select nombre,contraseña from persona", null)
        while (fila.moveToNext()) {
            if(nombre==fila.getString(0) && contraseña==fila.getString(1)){
                comprobar=true
            }
        }
        bd.close()
        return comprobar
    }



}