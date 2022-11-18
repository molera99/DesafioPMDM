package com.example.desafiopmdm

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityDetallespilotoBinding
import modelos.Nave
import modelos.Piloto

class DetallespilotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetallespilotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallespilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto

        binding.txtNombreD.text=piloto.nombre
        binding.txtEdadD.text=piloto.edad.toString()
        binding.txtExperienciaD.text=piloto.experiencia.toString()

        if(piloto.foto!="Defecto"){
            var fotoPiloto = BitmapFactory.decodeFile(piloto.foto)
            binding.ivFotoPilotoD.setImageBitmap(fotoPiloto)
        }else{
            val imageResource: Int = getResources().getIdentifier("@drawable/piloto", null, getPackageName())
            var res: Drawable = resources.getDrawable(imageResource)
            binding.ivFotoPilotoD.setImageDrawable(res)
        }

        binding.btAtrasPiloto.setOnClickListener{
            var intentAsignarMision = Intent(this,AsignarMisionActivity::class.java)
            startActivity(intentAsignarMision)
        }

    }
}