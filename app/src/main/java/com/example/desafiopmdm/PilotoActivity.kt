package com.example.desafiopmdm

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityPilotoBinding
import modelos.Piloto

class PilotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPilotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto
        binding.txtNombre.text=piloto.nombre
        if(piloto.foto!="Defecto"){
            var fotoPiloto = BitmapFactory.decodeFile(piloto.foto)
            binding.ivFotoPiloto.setImageBitmap(fotoPiloto)
        }else{
            val imageResource: Int = getResources().getIdentifier("@drawable/piloto", null, getPackageName())
            var res: Drawable = resources.getDrawable(imageResource)
            binding.ivFotoPiloto.setImageDrawable(res)
        }

    }
}