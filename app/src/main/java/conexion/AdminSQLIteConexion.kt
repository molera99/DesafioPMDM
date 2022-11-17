package conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table persona(nombre text primary key,dis text, edad int, contraseña text, experiencia int, foto text)")
        db.execSQL("create table nave( matricula text primary key,tipo text, carga int, pasajeros int,foto text)")
        db.execSQL("create table mision(idMision int primary key,dis text, nombre text, matricula text,duracion int,numCazas int,bombarderos int, foreign key (nombre) references persona (nombre), foreign key (matricula) references nave (matricula))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}