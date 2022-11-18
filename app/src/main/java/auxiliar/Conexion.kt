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

    fun addVuelo(contexto: AppCompatActivity, v:Vuelo){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("idMision", v.idMision)
        registro.put("tipo", v.tipo)
        registro.put("duracion",v.duracion)
        bd.insert("mision", null, registro)
        bd.close()
    }
    fun addBombardeo(contexto: AppCompatActivity, b:Bombardeo){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("idMision", b.idMision)
        registro.put("tipo", b.tipo)
        registro.put("bombarderos",b.numBombarderos)
        bd.insert("mision", null, registro)
        bd.close()
    }
    fun addCombate(contexto: AppCompatActivity, c:Combate){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("idMision", c.idMision)
        registro.put("tipo", c.tipo)
        registro.put("numCazas",c.numCazas)
        bd.insert("mision", null, registro)
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

        var fila = bd.rawQuery("select nombre,edad,contraseña,experiencia,foto from persona where dis not like \"Admin\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var p: Persona = Piloto(fila.getString(0), fila.getInt(1), fila.getString(2), fila.getInt(3), fila.getString(4))
                Listas.listaPersona.add(p)
            }
        }
        bd.close()

    }
    fun obtenerMisiones(contexto: AppCompatActivity){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        var fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Vuelo\"", null)
        if(fila.count>0) {
                while (fila.moveToNext()) {
                    var v: Vuelo = Vuelo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(5))
                    Listas.listaMision.add(v)
                }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Bombardeo\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var b: Bombardeo = Bombardeo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(7))
                Listas.listaMision.add(b)
            }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Combate\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var c: Combate = Combate(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(6))
                Listas.listaMision.add(c)
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
        bd.close()

    }

    fun obtenerMisionesSinRealizar(contexto: AppCompatActivity,nombre: String):ArrayList<Mision>{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var misiones=ArrayList<Mision>()
        var fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Vuelo\" and nombre=\"$nombre\" and resultado=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var v: Vuelo = Vuelo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(5))
                misiones.add(v)
            }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Bombardeo\" and nombre=\"$nombre\" and resultado=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var b: Bombardeo = Bombardeo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(7))
                misiones.add(b)
            }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Combate\" and nombre=\"$nombre\" and resultado=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var c: Combate = Combate(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(6))
                misiones.add(c)
            }
        }
        bd.close()
        return misiones
    }
    fun obtenerMisionesRealizadas(contexto: AppCompatActivity,nombre: String):ArrayList<Mision>{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var misiones=ArrayList<Mision>()
        var fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Vuelo\" and nombre=\"$nombre\" and resultado!=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var v: Vuelo = Vuelo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(5))
                misiones.add(v)
            }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Bombardeo\" and nombre=\"$nombre\" and resultado!=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var b: Bombardeo = Bombardeo(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(7))
                misiones.add(b)
            }
        }
        fila = bd.rawQuery("select idMision,tipo,nombre,matricula,resultado,duracion,numCazas,bombarderos from mision where tipo=\"Combate\" and nombre=\"$nombre\" and resultado!=\"No realizada\"", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                var c: Combate = Combate(fila.getInt(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4), fila.getInt(6))
                misiones.add(c)
            }
        }
        bd.close()
        return misiones
    }
    fun updateExperiencia(contexto:AppCompatActivity,piloto: Piloto){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("experiencia", piloto.experiencia)
        bd.update("persona", registro, "nombre='${piloto.nombre}'", null)
        bd.close()
    }

    fun UpdateMision(contexto:AppCompatActivity, m:Mision,nombre: String,matricula: String,resultado:String){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", nombre)
        registro.put("matricula", matricula)
        registro.put("resultado", resultado)
        bd.update("mision", registro, "idMision='${m.idMision}'", null)
        bd.close()
    }

    fun updateResultado(contexto:AppCompatActivity,resultado: String,m:Mision){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("resultado", resultado)
        bd.update("mision", registro, "idMision='${m.idMision}'", null)
        bd.close()
    }

    fun updateFoto(contexto:AppCompatActivity,foto:String,nombre:String){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("foto", foto)
        bd.update("persona", registro, "nombre='${nombre}'", null)
        bd.close()
    }

    fun comprobarMisionDisponible(contexto:AppCompatActivity):ArrayList<Int>{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var mision=ArrayList<Int>()
        var fila = bd.rawQuery("SELECT idMision from mision where nombre is NULL", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                mision.add(fila.getInt(0))
            }
        }
        bd.close()
        return mision
    }

    fun obtenerIdMision(contexto: AppCompatActivity):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        var idMision=0
        var fila = bd.rawQuery("select max(idMision) from mision", null)
        if(fila.count>0) {
            while (fila.moveToNext()) {
                idMision=fila.getInt(0)
            }
        }
        bd.close()
        return idMision
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