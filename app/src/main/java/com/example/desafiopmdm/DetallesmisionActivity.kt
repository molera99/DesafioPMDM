package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityAsignarmisionBinding
import com.example.desafiopmdm.databinding.ActivityDetallesmisionBinding
import com.example.desafiopmdm.databinding.ActivityDetallesnaveBinding
import modelos.*

class DetallesmisionActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetallesmisionBinding
    lateinit var mision:Mision
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesmisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mision = intent.getSerializableExtra("mision") as Mision
        binding.txtIdMisionD.text=mision.idMision.toString()
        binding.txtTipoMisionD.text=mision.tipo
        if(mision is Vuelo){
            binding.txtPropMision.text="Duracion:"
            binding.txtPropMisionD.text= (mision as Vuelo).duracion.toString()
        }else if(mision is Bombardeo){
            binding.txtPropMision.text="Numero de enemigos:"
            binding.txtPropMisionD.text= (mision as Bombardeo).numBombarderos.toString()
        }else if(mision is Combate){
            binding.txtPropMision.text="Numero de enemigos:"
            binding.txtPropMisionD.text= (mision as Combate).numCazas.toString()
        }

        binding.btAtrasMision.setOnClickListener{
            var intentAsignarMision = Intent(this,AsignarMisionActivity::class.java)
            startActivity(intentAsignarMision)
        }
    }
}