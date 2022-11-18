package com.example.desafiopmdm

import Adaptadores.AdaptadorMisionAcab
import Adaptadores.AdaptadorMisionSin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityMisionesreaBinding
import com.example.desafiopmdm.databinding.ActivityMisionessinBinding
import modelos.Mision
import modelos.Piloto

class MisionesReaActivity : AppCompatActivity() {
    lateinit var binding: ActivityMisionesreaBinding
    lateinit var miRecyclerView : RecyclerView
    var misiones=ArrayList<Mision>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisionesreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto

        misiones= Conexion.obtenerMisionesRealizadas(this,piloto.nombre)
        miRecyclerView = binding.rvMisionesRea as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = AdaptadorMisionAcab(misiones, this)
        miRecyclerView.adapter = miAdapter

        binding.btAtrasListaMisionRea.setOnClickListener{
            var intentPiloto = Intent(this,PilotoActivity::class.java)
            intentPiloto.putExtra("piloto",piloto)
            startActivity(intentPiloto)
        }
    }
}