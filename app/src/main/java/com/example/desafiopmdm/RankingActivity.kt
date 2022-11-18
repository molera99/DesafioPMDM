package com.example.desafiopmdm

import Adaptadores.AdaptadorRanking
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopmdm.databinding.ActivityRankingBinding
import modelos.Listas
import modelos.Piloto

class RankingActivity : AppCompatActivity() {
    lateinit var binding: ActivityRankingBinding
    lateinit var miRecyclerView : RecyclerView
    var pilotos=ArrayList<Piloto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        pilotos= Listas.listaPersona as ArrayList<Piloto>
        var pilotosOrdenados=pilotos.sortedBy { it.experiencia }
        miRecyclerView = binding.rvRanking as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = AdaptadorRanking(pilotosOrdenados, this)
        miRecyclerView.adapter = miAdapter

        binding.btAtras.setOnClickListener{
            var intentPiloto = Intent(this,VaderActivity::class.java)
            startActivity(intentPiloto)
        }
    }
}