package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * Clase correspondiente al fragment principal en el que se muestra la lista de vinos
 */
class HomeFragment : BaseFragment() {

    //boolean para controlar el estado del botón que encierra a los dos botones
    var isOpen = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Seteamos el título en el action bar
        (activity as AppCompatActivity).supportActionBar?.title = "Dioniso - Proyecto DAM"

        //Cargamos en los botones las animaciones que hemos creado en res/anim
        val fabOpen = AnimationUtils.loadAnimation(this.context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this.context, R.anim.fab_close)
        val fabRClockWise = AnimationUtils.loadAnimation(this.context, R.anim.rotate_clockwise)
        val fabRAntiClockWise = AnimationUtils.loadAnimation(this.context, R.anim.rotate_anticlockwise)

        fabAll.setOnClickListener {
            if (isOpen) {
                fabRed.startAnimation(fabClose)
                fabFav.startAnimation(fabClose)
                fabAll.startAnimation(fabRClockWise)

                isOpen = false
            }

            else {
                fabRed.startAnimation(fabOpen)
                fabFav.startAnimation(fabOpen)
                fabAll.startAnimation(fabRAntiClockWise)

                fabRed.isClickable
                fabFav.isClickable

                isOpen = true
            }

            fabRed.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionAddVino()
                Navigation.findNavController(it).navigate(action)
            }

            fabFav.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionfav()
                Navigation.findNavController(it).navigate(action)
            }

        }

        vinosRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        launch {
            context?.let {
                val vinos = VinoDatabase(it).getVinoDao().getAllVinos()
                vinosRV.adapter = VinosAdapter(vinos)
            }
        }
    }
}
