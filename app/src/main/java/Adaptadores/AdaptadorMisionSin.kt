package Adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopmdm.*
import modelos.Mision

class AdaptadorMisionSin(var misiones : ArrayList<Mision>,var  context: Context) : RecyclerView.Adapter<AdaptadorMisionSin.ViewHolder>() {

    companion object {
        //Esta variable estática nos será muy útil para saber cual está marcado o no.
        var seleccionado:Int = -1
        /*
        PAra marcar o desmarcar un elemento de la lista lo haremos diferente a una listView. En la listView el listener
        está en la activity por lo que podemos controlar desde fuera el valor de seleccionado y pasarlo al adapter, asociamos
        el adapter a la listview y resuelto.
        En las RecyclerView usamos para pintar cada elemento la función bind (ver código más abajo, en la clase ViewHolder).
        Esto se carga una vez, solo una vez, de ahí la eficiencia de las RecyclerView. Si queremos que el click que hagamos
        se vea reflejado debemos recargar la lista, para ello forzamos la recarga con el método: notifyDataSetChanged().
         */
    }


    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = misiones.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_card,parent,false))
        //return ViewHolder(layoutInflater.inflate(R.layout.item_card,parent,false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return misiones.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Esto solo se asocia la primera vez que se llama a la clase, en el método onCreate de la clase que contiene a esta.
        //Por eso no hace falta que hagamos lo que hacíamos en el método getView de los adaptadores para las listsViews.
        val tipoMision = view.findViewById(R.id.txtMisionCard) as TextView

        //Como en el ejemplo general de las listas (ProbandoListas) vemos que se puede inflar cada elemento con una card o con un layout.
        //val nombrePersonaje = view.findViewById(R.id.txtNombreCard) as TextView
        //val tipoPersonaje = view.findViewById(R.id.txtRazaCard) as TextView
        //val avatar = view.findViewById(R.id.imagePersonajeCard) as ImageView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(mis:Mision, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorMisionSin){
            tipoMision.text = mis.tipo

            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == AdaptadorMisionSin.seleccionado) {
                with(tipoMision) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(tipoMision) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == AdaptadorMisionSin.seleccionado){
                    AdaptadorMisionSin.seleccionado = -1
                }
                else {
                    AdaptadorMisionSin.seleccionado = pos
                    var intentDetalles = Intent(context, DetallesMisionSinActivity::class.java)
                    intentDetalles.putExtra("mision",mis)
                    context.startActivity(intentDetalles)
                    AdaptadorMisionSin.seleccionado = -1
                }
                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

            })
            fun simular(){
                var intentSimulacion = Intent(context, SimulacionActivity::class.java)
                intentSimulacion.putExtra("mision",mis)
                context.startActivity(intentSimulacion)
            }
            itemView.setOnLongClickListener(View.OnLongClickListener
            {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                with(builder){
                    builder.setTitle("Iniciar simulacion")
                    builder.setMessage("¿Desea iniciar la simulacion?")
                    builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener(function={ dialog: DialogInterface, which:Int -> simular()}))
                    builder.setNegativeButton("Cancelar", null)
                    show()
                }
                miAdaptadorRecycler.notifyDataSetChanged()
                return@OnLongClickListener true
            })
        }
    }
}