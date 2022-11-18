package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityMainBinding
import com.example.desafiopmdm.databinding.ActivityVaderBinding

class VaderActivity : AppCompatActivity() {
    lateinit var binding: ActivityVaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAltaPiloto.setOnClickListener{
            var intentAñadirPiloto = Intent(this,AddpilotoActivity::class.java)
            startActivity(intentAñadirPiloto)
        }
        binding.btAltaNave.setOnClickListener{
            var intentAñadirNave = Intent(this,NaveActivity::class.java)
            startActivity(intentAñadirNave)
        }
        binding.btAAdirMision.setOnClickListener{
            var intentAñadirMision = Intent(this,MisionesActivity::class.java)
            startActivity(intentAñadirMision)
        }
        binding.btAsignarMision.setOnClickListener{
            var intentAsignarMision = Intent(this,AsignarMisionActivity::class.java)
            startActivity(intentAsignarMision)
        }
    }
}