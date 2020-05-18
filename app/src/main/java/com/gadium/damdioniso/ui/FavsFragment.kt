package com.gadium.damdioniso.ui

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_favs.*
import kotlinx.coroutines.launch

/**
 * Clase correspondiente al fragmento que muestra los vinos favoritos
 */
class FavsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Seteamos el título en el action bar
        (activity as AppCompatActivity).supportActionBar?.title ="Mis Vinos Favoritos"

        favsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        launch {
            context?.let {
                val vinos = VinoDatabase(it).getVinoDao().getAllVinosFav()
                favsRV.adapter = VinosAdapter(vinos)
            }
        }

    }

}
