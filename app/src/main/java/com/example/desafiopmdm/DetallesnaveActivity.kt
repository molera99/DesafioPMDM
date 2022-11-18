package com.example.desafiopmdm

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityDetallesnaveBinding
import com.example.desafiopmdm.databinding.ActivityDetallespilotoBinding
import modelos.Nave

class DetallesnaveActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetallesnaveBinding
    lateinit var nave: Nave
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesnaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nave = intent.getSerializableExtra("nave") as Nave


        binding.txtMatriculaD.text=nave.matricula
        binding.txtTipoD.text=nave.tipo
        if (nave.carga){
            binding.txtCargaD.text="Si"
        }else{
            binding.txtCargaD.text="No"
        }
        if (nave.pasajeros){
            binding.txtPasajerosD.text="Si"
        }else{
            binding.txtPasajerosD.text="No"
        }

        when(nave.foto){
            "DefectoCaza" -> cargarImagen(uri = "@drawable/caza")
            "DefectoBombardero" -> cargarImagen(uri = "@drawable/bombardero")
            "DefectoTransporte" -> cargarImagen(uri = "@drawable/transporte")
            else -> cargarImagenPer()
        }

        binding.btAtrasNave.setOnClickListener{
            var intentAsignarMision = Intent(this,AsignarMisionActivity::class.java)
            startActivity(intentAsignarMision)
        }
    }

    fun cargarImagen(uri:String){
        val imageResource: Int = getResources().getIdentifier(uri, null, getPackageName())
        var res: Drawable = resources.getDrawable(imageResource)
        binding.ivFotoNaveD.setImageDrawable(res)
    }

    fun cargarImagenPer(){
        var fotoNave = BitmapFactory.decodeFile(nave.foto)
        binding.ivFotoNaveD.setImageBitmap(fotoNave)
    }
}