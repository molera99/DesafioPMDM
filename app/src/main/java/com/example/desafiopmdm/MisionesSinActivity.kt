package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityMisionessinBinding

class MisionesSinActivity : AppCompatActivity() {
    lateinit var binding: ActivityMisionessinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisionessinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btAtrasListaMisionSin.setOnClickListener{
            var intentPiloto = Intent(this,PilotoActivity::class.java)
            startActivity(intentPiloto)
        }
    }
}