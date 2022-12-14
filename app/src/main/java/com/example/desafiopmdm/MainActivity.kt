package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import auxiliar.Conexion
import auxiliar.ContadorId
import com.example.desafiopmdm.databinding.ActivityMainBinding
import factorias.FactoriaPersonas
import modelos.Listas
import modelos.Piloto
import modelos.Vader
import modelos.Vuelo

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var comprobarVader=Conexion.comprobarVader(this)
        if (!comprobarVader){
            var vader=FactoriaPersonas.crearVader()
            Conexion.addVader(this,vader)
        }
        Conexion.obtenerPersonas(this)
        Conexion.obtenerNaves(this)
        Conexion.obtenerMisiones(this)
        ContadorId.idMision=Conexion.obtenerIdMision(this)
        binding.btIniciar.setOnClickListener{
                var comprobarCredenciales=Conexion.comprobarCredenciales(this,binding.etUsuario.text.toString(),binding.etPassword.text.toString())
                if(comprobarCredenciales && binding.etUsuario.text.toString()=="Vader"){
                    var intentVader = Intent(this,VaderActivity::class.java)
                    startActivity(intentVader)
                }else if(comprobarCredenciales && binding.etUsuario.text.toString()!="Vader"){
                    var piloto: Piloto? =null
                    for(i in Listas.listaPersona.indices){
                        if(Listas.listaPersona[i].nombre==binding.etUsuario.text.toString()){
                            piloto= Listas.listaPersona[i] as Piloto
                        }
                    }
                    var intentPiloto = Intent(this,PilotoActivity::class.java)
                    intentPiloto.putExtra("piloto",piloto)
                    startActivity(intentPiloto)
                } else{
                    Toast.makeText(this, "Usuario o contrase??a incorrectos", Toast.LENGTH_SHORT).show()
                }


        }
    }
}