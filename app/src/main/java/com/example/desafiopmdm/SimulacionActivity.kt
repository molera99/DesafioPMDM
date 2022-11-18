package com.example.desafiopmdm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import auxiliar.Conexion
import com.example.desafiopmdm.databinding.ActivityDetallesmisionBinding
import com.example.desafiopmdm.databinding.ActivitySimulacionBinding
import modelos.*
import kotlin.random.Random

class SimulacionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimulacionBinding
    lateinit var mision:Mision
    lateinit var piloto:Piloto
    lateinit var nave: Nave
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimulacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mision = intent.getSerializableExtra("mision") as Mision
        for (i in Listas.listaPersona.indices){
            if (Listas.listaPersona[i].nombre==mision.nombre){
                piloto=Listas.listaPersona[i] as Piloto
            }
        }
        for (i in Listas.listaNave.indices){
            if (Listas.listaNave[i].matricula==mision.matricula){
                nave=Listas.listaNave[i]
            }
        }

        binding.txtPilotoSim.text=piloto.nombre
        binding.txtNaveSim.text=nave.tipo
        binding.txtMisionSim.text=mision.tipo

        if (mision is Vuelo){
            binding.pbProgreso.setProgress(0)
            var duracion=(mision as Vuelo).duracion
            progresoVuelo(duracion)
        }else if(mision is Bombardeo){
            binding.pbProgreso.setProgress(0)
            var numBombardeos= (mision as Bombardeo).numBombarderos
            progresoBombardeo(numBombardeos)
        }else if(mision is Combate){
            binding.pbProgreso.setProgress(0)
            var numCazas= (mision as Combate).numCazas
            progresoCombate(numCazas)

        }
            binding.btFinalizarSimulacion.setOnClickListener{
                var intentPiloto = Intent(this,PilotoActivity::class.java)
                intentPiloto.putExtra("piloto",piloto)
                startActivity(intentPiloto)
            }



    }

    @SuppressLint("SetTextI18n")
    private fun progresoVuelo(duracion:Int){
        var tiempo=0
        var probabilidad:Int
        Thread(Runnable {

            run(){
                while(tiempo<duracion) {
                    if(tiempo % 1000 ==0){
                        binding.txtProgreso.text="Volando"
                        Thread.sleep(1000)
                        binding.pbProgreso.incrementProgressBy(1000)
                    }
                    if (tiempo % 10000 == 0) {
                        binding.txtProgreso.text="Tormenta solar"
                        Thread.sleep(10000)
                        binding.pbProgreso.incrementProgressBy(10000)
                        if (nave.tormentaSolar(piloto)){
                            binding.txtProgreso.text="Tormenta solar superada"
                        }else{
                            binding.txtProgreso.text="Nuestra nave ha caido"
                            binding.pbProgreso.setProgress(100000)
                            break
                        }
                    }
                    if (tiempo % 20000 == 0) {
                        binding.txtProgreso.text="Volando"
                        Thread.sleep(20000)
                        binding.pbProgreso.incrementProgressBy(20000)
                        probabilidad = Random.nextInt(1, 11)
                        if (probabilidad in 1..3){
                            binding.txtProgreso.text="Sufriendo ataque"
                            if(nave.ataqueAereo(piloto)) {
                                binding.txtProgreso.text="Atque superado con exito"
                            }else{
                                binding.txtProgreso.text="Nuestra nave ha caido"
                                binding.pbProgreso.setProgress(100000)
                                break
                            }
                        }else{
                            binding.txtProgreso.text="No se ha sufrido ningun ataque"
                        }
                    }

                    tiempo++
                }
                if(tiempo<duracion){
                    binding.txtResultado.text="Resultado: Mision Fallida"
                    Conexion.updateResultado(this, resultado = "Fallido",mision)
                }else{
                    binding.txtResultado.text="Resultado: Mision Superada"
                    Conexion.updateResultado(this, resultado = "Superado",mision)
                    piloto.sumarExperienciaVuelo(nave)
                    Conexion.updateExperiencia(this,piloto)
                }

            }
        }).start()

    }

    @SuppressLint("SetTextI18n")
    private fun progresoBombardeo(numBombardeos:Int){
        var tiempo=0
        var bombarderos=0
        Thread(Runnable {

            run(){
                while(tiempo<100000 || bombarderos<numBombardeos) {
                    if(tiempo % 1000 ==0){
                        Thread.sleep(1000)
                        binding.pbProgreso.incrementProgressBy(1000)
                    }
                    if (tiempo % 5000 == 0) {
                        binding.txtProgreso.text="Combatiendo"
                        Thread.sleep(5000)
                        binding.pbProgreso.incrementProgressBy(5000)
                        if (nave.combateBombardeo(piloto)){
                            binding.txtProgreso.text="Bombardero abatido con exito"
                            bombarderos++
                        }else{
                            binding.txtProgreso.text="Nuestro bombardero a sido derribado"
                            binding.pbProgreso.setProgress(100000)
                            break
                        }
                    }


                    tiempo++
                }
                if(bombarderos!=numBombardeos){
                    binding.txtResultado.text="Resultado: Mision Fallida"
                    Conexion.updateResultado(this, resultado = "Fallido",mision)
                    binding.txtDetalles.text="bombarderos derribados: $bombarderos"
                }else{
                    binding.txtResultado.text="Resultado: Mision Superada"
                    Conexion.updateResultado(this, resultado = "Superado",mision)
                    binding.txtDetalles.text="bombarderos derribados: $bombarderos"
                }
                piloto.sumaExperienciaBombardero(bombarderos,nave)
                Conexion.updateExperiencia(this,piloto)
            }
        }).start()

    }

    @SuppressLint("SetTextI18n")
    private fun progresoCombate(numCazas:Int){
        var tiempo=0
        var cazas=0
        Thread(Runnable {

            run(){
                while(tiempo<100000 || cazas<numCazas) {
                    if(tiempo % 1000 ==0){
                        Thread.sleep(1000)
                        binding.pbProgreso.incrementProgressBy(1000)
                    }
                    if (tiempo % 5000 == 0) {
                        binding.txtProgreso.text="Combatiendo"
                        Thread.sleep(5000)
                        binding.pbProgreso.incrementProgressBy(5000)
                        if (nave.combateCaza(piloto)){
                            binding.txtProgreso.text="Caza abatido con exito"
                            cazas++
                        }else{
                            binding.txtProgreso.text="Nuestro caza a sido derribado"
                            binding.pbProgreso.setProgress(100000)
                            break
                        }
                    }


                    tiempo++
                }
                if(cazas!=numCazas){
                    binding.txtResultado.text="Resultado: Mision Fallida"
                    Conexion.updateResultado(this, resultado = "Fallido",mision)
                    binding.txtDetalles.text="Cazas derribados: $cazas"
                }else{
                    binding.txtResultado.text="Resultado: Mision Superada"
                    Conexion.updateResultado(this, resultado = "Superado",mision)
                    binding.txtDetalles.text="Cazas derribados: $cazas"
                }
                piloto.sumarExperienciaCombate(cazas)
                Conexion.updateExperiencia(this,piloto)
            }
        }).start()

    }



}