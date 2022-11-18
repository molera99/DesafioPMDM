package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityDetallesmisionBinding
import com.example.desafiopmdm.databinding.ActivityDetallesmisionsinBinding
import modelos.Bombardeo
import modelos.Combate
import modelos.Mision
import modelos.Vuelo

class DetallesMisionSinActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetallesmisionsinBinding
    lateinit var mision: Mision
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesmisionsinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mision = intent.getSerializableExtra("mision") as Mision
        binding.txtIdMisionD2.text=mision.idMision.toString()
        binding.txtTipoMisionD2.text=mision.tipo
        if(mision is Vuelo){
            binding.txtPropMision2.text="Duracion:"
            binding.txtPropMisionD2.text= (mision as Vuelo).duracion.toString()
        }else if(mision is Bombardeo){
            binding.txtPropMision2.text="Numero de enemigos:"
            binding.txtPropMisionD2.text= (mision as Bombardeo).numBombarderos.toString()
        }else if(mision is Combate){
            binding.txtPropMision2.text="Numero de enemigos:"
            binding.txtPropMisionD2.text= (mision as Combate).numCazas.toString()
        }

        binding.btAtrasMision2.setOnClickListener{
            finish()
        }
    }
}