package com.example.desafiopmdm

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityDetallespilotoBinding
import com.example.desafiopmdm.databinding.ActivityDetallesrankingBinding
import com.example.desafiopmdm.databinding.ActivityRankingBinding
import modelos.Piloto

class DetallesRankingActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetallesrankingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesrankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto

        binding.txtNombreD2.text=piloto.nombre
        binding.txtEdadD2.text=piloto.edad.toString()
        binding.txtExperienciaD2.text=piloto.experiencia.toString()

        if(piloto.foto!="Defecto"){
            var fotoPiloto = BitmapFactory.decodeFile(piloto.foto)
            binding.ivFotoPilotoD2.setImageBitmap(fotoPiloto)
        }else{
            val imageResource: Int = getResources().getIdentifier("@drawable/piloto", null, getPackageName())
            var res: Drawable = resources.getDrawable(imageResource)
            binding.ivFotoPilotoD2.setImageDrawable(res)
        }

        binding.btAtrasPiloto2.setOnClickListener{
            var intentAsignarMision = Intent(this,RankingActivity::class.java)
            startActivity(intentAsignarMision)
        }
    }
}