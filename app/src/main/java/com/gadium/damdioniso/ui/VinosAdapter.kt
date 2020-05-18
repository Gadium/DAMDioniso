package com.gadium.damdioniso.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.Vino
import kotlinx.android.synthetic.main.item_vino.view.*

/**
 * Clase con la que gestionamos la lista de vinos que se muestran en el HomeFragment
 */
class VinosAdapter(val vinos: List<Vino>): RecyclerView.Adapter<VinosAdapter.VinoViewHolder>() {

    class VinoViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VinoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_vino, parent, false)
        return VinoViewHolder(view)
    }

    override fun getItemCount() = vinos.size

    //Mostramos los datos en la lista
    override fun onBindViewHolder(holder: VinoViewHolder, position: Int) {
        holder.view.textView.text = vinos[position].nombre
        holder.view.textView3.text = vinos[position].bodega
        holder.view.textView2.text = vinos[position].crianza

        //En función del tipo de vino mostraremos un icono que lo identifique
        if (vinos[position].tipo == "Tinto"){
            holder.view.imageView.setImageResource(R.drawable.ic_001_wine_glass)
        }
        if (vinos[position].tipo == "Rosado"){
            holder.view.imageView.setImageResource(R.drawable.ic_001_wine_glass_pink)
        }
        if (vinos[position].tipo == "Blanco"){
            holder.view.imageView.setImageResource(R.drawable.ic_001_wine_glass_white)
        }
        if (vinos[position].tipo == "Azul"){
            holder.view.imageView.setImageResource(R.drawable.ic_001_wine_glass_blue)
        }
        if (vinos[position].tipo == "Espumoso"){
            holder.view.imageView.setImageResource(R.drawable.ic_001_wine_glass_white_champagne)
        }
        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddVino()
            action.vino = vinos[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
}