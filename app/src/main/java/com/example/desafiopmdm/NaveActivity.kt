package com.example.desafiopmdm

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityNaveBinding
import factorias.FactoriaNaves
import modelos.Listas
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class NaveActivity : AppCompatActivity() {
    lateinit var binding: ActivityNaveBinding
    private val cameraRequest = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cbCarga.isEnabled=false
        binding.cbPasajeros.isEnabled=false

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)

        binding.rbCaza.setOnClickListener{
            binding.cbCarga.isEnabled=false
            binding.cbPasajeros.isEnabled=false
            if (binding.swFotoNave.isChecked){
                val imageResource: Int = getResources().getIdentifier("@drawable/caza", null, getPackageName())
                var res: Drawable = resources.getDrawable(imageResource)
                binding.ivFotoNave.setImageDrawable(res)
            }
        }
        binding.rbBombardero.setOnClickListener{
            binding.cbCarga.isEnabled=true
            binding.cbPasajeros.isEnabled=true
            if (binding.swFotoNave.isChecked){
                val imageResource: Int = getResources().getIdentifier("@drawable/bombardero", null, getPackageName())
                var res: Drawable = resources.getDrawable(imageResource)
                binding.ivFotoNave.setImageDrawable(res)
            }
        }
        binding.rbTransporte.setOnClickListener{
            binding.cbCarga.isEnabled=true
            binding.cbPasajeros.isEnabled=true
            if (binding.swFotoNave.isChecked){
                val imageResource: Int = getResources().getIdentifier("@drawable/transporte", null, getPackageName())
                var res: Drawable = resources.getDrawable(imageResource)
                binding.ivFotoNave.setImageDrawable(res)
            }
        }

        binding.ivFotoNave.setOnClickListener{
            if (binding.etMatricula.text.toString().isEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                with(builder){
                    builder.setTitle("ERROR")
                    builder.setMessage("Primero debes rellenar el campo Matricula")
                    builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener{ dialog, id -> })
                    show()
                }
            }else{
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, cameraRequest)
            }
        }

        binding.btAAdirNave.setOnClickListener{
            var matricula=binding.etMatricula.text.toString()
            var tipo=""
            var foto=""

            if(binding.rbCaza.isChecked){
                tipo="Caza"
                foto="DefectoCaza"
            }else if(binding.rbBombardero.isChecked){
                tipo="Bombardero"
                foto="DefectoBombardero"
            }else if(binding.rbTransporte.isChecked){
                tipo="Transporte"
                foto="DefectoTransporte"
            }

            var carga=false
            var pasajeros=false

            if (binding.cbCarga.isChecked){
                carga=true
            }
            if (binding.cbPasajeros.isChecked){
                pasajeros=true
            }

            if (!binding.swFotoNave.isChecked){
                foto=getExternalFilesDir(null).toString() + "/"+binding.etMatricula.text.toString()
            }

            var nave=FactoriaNaves.crearNave(matricula, tipo, carga, pasajeros, foto)
            Listas.listaNave.add(nave)
            Conexion.addNave(this,nave)

            Toast.makeText(this, "Nave $matricula añadido con exito", Toast.LENGTH_SHORT).show()
            var intentVader = Intent(this,VaderActivity::class.java)
            startActivity(intentVader)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                binding.ivFotoNave.setImageBitmap(photo)

                var fotoFichero = File(getExternalFilesDir(null), binding.etMatricula.text.toString())
                var uri = Uri.fromFile(fotoFichero)
                var fileOutStream = FileOutputStream(fotoFichero)
                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                fileOutStream.flush();
                fileOutStream.close();
            }
        }catch(e: Exception){
            Log.e("David",e.toString())
        }
    }
}