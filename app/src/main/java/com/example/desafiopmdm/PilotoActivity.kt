package com.example.desafiopmdm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityPilotoBinding
import modelos.Piloto
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class PilotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPilotoBinding
    private val cameraRequest = 1888
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)

        var piloto: Piloto = intent.getSerializableExtra("piloto") as Piloto
        binding.txtNombreP.text=piloto.nombre
        binding.txtEdadP.text=piloto.edad.toString()
        binding.txtExperienciaP.text=piloto.experiencia.toString()
        if(piloto.foto!="Defecto"){
            var fotoPiloto = BitmapFactory.decodeFile(piloto.foto)
            binding.ivFotoPiloto.setImageBitmap(fotoPiloto)
        }else{
            val imageResource: Int = getResources().getIdentifier("@drawable/piloto", null, getPackageName())
            var res: Drawable = resources.getDrawable(imageResource)
            binding.ivFotoPiloto.setImageDrawable(res)
        }

        binding.btCambiarFoto2.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        binding.btMisionesNo.setOnClickListener{
            var intentMisionesNoAsig = Intent(this,MisionesSinActivity::class.java)
            intentMisionesNoAsig.putExtra("piloto",piloto)
            startActivity(intentMisionesNoAsig)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                binding.ivFotoPiloto.setImageBitmap(photo)

                var fotoFichero = File(getExternalFilesDir(null), binding.txtNombreP.text.toString())
                var uri = Uri.fromFile(fotoFichero)
                var fileOutStream = FileOutputStream(fotoFichero)
                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                fileOutStream.flush();
                fileOutStream.close();
                Conexion.updateFoto(this,getExternalFilesDir(null).toString() + "/"+binding.txtNombreP.text.toString(),binding.txtNombreP.text.toString())
            }
        }catch(e: Exception){
            Log.e("David",e.toString())
        }
    }
}