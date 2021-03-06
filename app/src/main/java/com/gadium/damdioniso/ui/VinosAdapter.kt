package com.gadium.damdioniso.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.Vino
import kotlinx.android.synthetic.main.fragment_add_vino.view.*
import kotlinx.android.synthetic.main.item_vino.view.*

class VinosAdapter(val vinos: List<Vino>): RecyclerView.Adapter<VinosAdapter.VinoViewHolder>() {

    class VinoViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VinoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_vino, parent, false)
        return VinoViewHolder(view)
    }

    override fun getItemCount() = vinos.size

    override fun onBindViewHolder(holder: VinoViewHolder, position: Int) {
        holder.view.textView.text = vinos[position].nombre
        holder.view.textView3.text = vinos[position].bodega
        holder.view.textView2.text = vinos[position].crianza
        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddVino()
            action.vino = vinos[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
}