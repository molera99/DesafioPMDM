package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityMisionesBinding
import factorias.FactoriaMisiones
import modelos.*

class MisionesActivity : AppCompatActivity() {
    lateinit var binding: ActivityMisionesBinding
    lateinit var vuelo: Vuelo
    lateinit var bombardeo: Bombardeo
    lateinit var combate: Combate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDurVuelo.isEnabled=false
        binding.etNumBombarderos.isEnabled=false
        binding.etNumCazas.isEnabled=false

        binding.rbVuelo.setOnClickListener{
            binding.etDurVuelo.isEnabled=true
            binding.etNumBombarderos.isEnabled=false
            binding.etNumCazas.isEnabled=false
        }

        binding.rbBombardeo.setOnClickListener{
            binding.etDurVuelo.isEnabled=false
            binding.etNumBombarderos.isEnabled=true
            binding.etNumCazas.isEnabled=false
        }

        binding.rbCombate.setOnClickListener{
            binding.etDurVuelo.isEnabled=false
            binding.etNumBombarderos.isEnabled=false
            binding.etNumCazas.isEnabled=true
        }

        binding.btAddMision.setOnClickListener{
            if (binding.rbVuelo.isChecked){
                vuelo=FactoriaMisiones.crearVuelo(binding.etDurVuelo.text.toString().toInt()*1000)
                Listas.listaMision.add(vuelo)
                Conexion.addVuelo(this,vuelo)
                Toast.makeText(this, "Mision ${vuelo.tipo} añadida con exito", Toast.LENGTH_SHORT).show()
            }else if(binding.rbBombardeo.isChecked){
                bombardeo=FactoriaMisiones.crearBombardeo(binding.etNumBombarderos.text.toString().toInt())
                Listas.listaMision.add(bombardeo)
                Conexion.addBombardeo(this,bombardeo)
                Toast.makeText(this, "Mision ${bombardeo.tipo} añadida con exito", Toast.LENGTH_SHORT).show()
            }else if(binding.rbCombate.isChecked){
                combate=FactoriaMisiones.crearCombate(binding.etNumCazas.text.toString().toInt())
                Listas.listaMision.add(combate)
                Conexion.addCombate(this,combate)
                Toast.makeText(this, "Mision ${combate.tipo} añadida con exito", Toast.LENGTH_SHORT).show()
            }

            var intentVader = Intent(this,VaderActivity::class.java)
            startActivity(intentVader)
        }

    }
}