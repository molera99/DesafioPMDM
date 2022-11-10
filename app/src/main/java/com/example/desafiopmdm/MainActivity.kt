package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityMainBinding
import factorias.FactoriaPersonas
import modelos.Vader

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

        binding.btIniciar.setOnClickListener{
                var comprobarCredenciales=Conexion.comprobarCredenciales(this,binding.etUsuario.text.toString(),binding.etPassword.text.toString())
                if(comprobarCredenciales && binding.etUsuario.text.toString()=="Vader"){
                    var intentVader = Intent(this,VaderActivity::class.java)
                    startActivity(intentVader)
                }else if(comprobarCredenciales && binding.etUsuario.text.toString()!="Vader"){

                } else{
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }


        }
    }
}