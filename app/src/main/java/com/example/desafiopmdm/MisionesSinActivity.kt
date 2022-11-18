package com.example.desafiopmdm

import Adaptadores.AdaptadorMisionSin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityMisionessinBinding
import modelos.Mision
import modelos.Piloto

class MisionesSinActivity : AppCompatActivity() {
    lateinit var binding: ActivityMisionessinBinding
    lateinit var miRecyclerView : RecyclerView
    var misiones=ArrayList<Mision>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisionessinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto

        misiones=Conexion.obtenerMisionesSinRealizar(this,piloto.nombre)
        miRecyclerView = binding.rvMisionesSin as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = AdaptadorMisionSin(misiones, this)
        miRecyclerView.adapter = miAdapter

        binding.btAtrasListaMisionSin.setOnClickListener{
            var intentPiloto = Intent(this,PilotoActivity::class.java)
            intentPiloto.putExtra("piloto",piloto)
            startActivity(intentPiloto)
        }
    }
}