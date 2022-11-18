package com.example.desafiopmdm

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityMainBinding
import modelos.Listas
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class AddpilotoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddpilotoBinding
    private val cameraRequest = 1888
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddpilotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)

        binding.sbExperiencia.max=150
        binding.sbExperiencia.progress=1

        binding.sbExperiencia.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtExperienciaNumero.text=progress.toString()
                if (progress<50){
                    binding.txtExperiencia.text="Novato"
                }else if(progress>50 && progress<100){
                    binding.txtExperiencia.text="Intermedio"
                }else{
                    binding.txtExperiencia.text="Experto"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        binding.ivFoto.setOnClickListener{
            if (binding.etNombre.text.toString().isEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                with(builder){
                    builder.setTitle("ERROR")
                    builder.setMessage("Primero debes rellenar el campo nombre")
                    builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{dialog, id -> })
                    show()
                }
            }else{
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, cameraRequest)
            }

        }
        binding.swFoto.setOnClickListener{
            if(binding.swFoto.isChecked){
                val imageResource: Int = getResources().getIdentifier("@drawable/piloto", null, getPackageName())
                var res: Drawable = resources.getDrawable(imageResource)
                binding.ivFoto.setImageDrawable(res)
            }else{
                val imageResource: Int = getResources().getIdentifier("@android:drawable/ic_input_add", null, getPackageName())
                var res: Drawable = resources.getDrawable(imageResource)
                binding.ivFoto.setImageDrawable(res)
            }
        }


        binding.btAAdirPiloto.setOnClickListener{

                var nombre = binding.etNombre.text.toString()
                var edad = binding.etEdad.text.toString().toInt()
                var contraseña = binding.etContraseA.text.toString()
                var experiencia = binding.sbExperiencia.progress
                var foto = ""
                if (binding.swFoto.isChecked) {
                    foto = "Defecto"
                } else {
                    foto =
                        getExternalFilesDir(null).toString() + "/" + binding.etNombre.text.toString()
                }
                var piloto = factorias.FactoriaPersonas.crearPiloto(
                    nombre,
                    edad,
                    contraseña,
                    experiencia,
                    foto
                )
                Listas.listaPersona.add(piloto)
                Conexion.addPiloto(this, piloto)
                Toast.makeText(this, "Piloto $nombre añadido con exito", Toast.LENGTH_SHORT).show()
                var intentVader = Intent(this, VaderActivity::class.java)
                startActivity(intentVader)


        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == cameraRequest) {
                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                binding.ivFoto.setImageBitmap(photo)

                var fotoFichero = File(getExternalFilesDir(null), binding.etNombre.text.toString())
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