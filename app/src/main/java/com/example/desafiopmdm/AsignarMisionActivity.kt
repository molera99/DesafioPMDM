package com.example.desafiopmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityAddpilotoBinding
import com.example.desafiopmdm.databinding.ActivityAsignarmisionBinding
import modelos.*

class AsignarMisionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAsignarmisionBinding
    lateinit var piloto:Piloto
    lateinit var nave: Nave
    lateinit var mision: Mision
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsignarmisionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var listaPersonas= ArrayList<String>()
        var listaNaves= ArrayList<String>()
        var listaMisiones=Conexion.comprobarMisionDisponible(this)

        var listaMisionDisponible=ArrayList<Mision>()

        for (i in Listas.listaPersona.indices){
            listaPersonas.add(Listas.listaPersona[i].nombre)
        }
        for (i in Listas.listaNave.indices){
            listaNaves.add(Listas.listaNave[i].matricula)
        }

        for (i in Listas.listaMision.indices){
            for (j in listaMisiones.indices){
                if (Listas.listaMision[i].idMision==listaMisiones[j]){
                    listaMisionDisponible.add(Listas.listaMision[i])
                }
            }
        }

        val adaptadorPilotos = ArrayAdapter(this, R.layout.item_spinner,R.id.txtItemSpinner,listaPersonas)
        binding.spPilotos.adapter = adaptadorPilotos

        val adaptadorNaves = ArrayAdapter(this, R.layout.item_spinner,R.id.txtItemSpinner,listaNaves)
        binding.spNaves.adapter = adaptadorNaves

        val adaptadorMisiones = ArrayAdapter(this, R.layout.item_spinner,R.id.txtItemSpinner,listaMisiones)
        binding.spMisiones.adapter = adaptadorMisiones

        binding.spPilotos.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                piloto= Listas.listaPersona[pos] as Piloto
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        binding.spNaves.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                nave= Listas.listaNave[pos]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        binding.spMisiones.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mision= listaMisionDisponible[pos]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        binding.btDetallesPiloto.setOnClickListener{
            var intentDetallesPiloto = Intent(this,DetallespilotoActivity::class.java)
            intentDetallesPiloto.putExtra("piloto",piloto)
            startActivity(intentDetallesPiloto)
        }

        binding.btDetallesNave.setOnClickListener{
            var intentDetallesNave = Intent(this,DetallesnaveActivity::class.java)
            intentDetallesNave.putExtra("nave",nave)
            startActivity(intentDetallesNave)
        }

        binding.btDetallesMisiones.setOnClickListener{
            var intentDetallesMision = Intent(this,DetallesmisionActivity::class.java)
            intentDetallesMision.putExtra("mision",mision)
            startActivity(intentDetallesMision)
        }
        binding.btAsigMision.setOnClickListener{
            Conexion.UpdateMision(this,mision,piloto.nombre,nave.matricula, resultado = "No realizada")
            Toast.makeText(this, "Mision asignada con exito", Toast.LENGTH_SHORT).show()
            var intentVader = Intent(this,VaderActivity::class.java)
            startActivity(intentVader)
        }
    }
}