package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

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

        val fabOpen = AnimationUtils.loadAnimation(this.context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this.context, R.anim.fab_close)
        val fabRClockWise = AnimationUtils.loadAnimation(this.context, R.anim.rotate_clockwise)
        val fabRAntiClockWise = AnimationUtils.loadAnimation(this.context, R.anim.rotate_anticlockwise)

        fabAll.setOnClickListener {
            if (isOpen) {
                fabRed.startAnimation(fabClose)
                fabWhite.startAnimation(fabClose)
                fabBlue.startAnimation(fabClose)
                fabFav.startAnimation(fabClose)
                fabAll.startAnimation(fabRClockWise)

                isOpen = false
            }

            else {
                fabRed.startAnimation(fabOpen)
                fabWhite.startAnimation(fabOpen)
                fabBlue.startAnimation(fabOpen)
                fabFav.startAnimation(fabOpen)
                fabAll.startAnimation(fabRAntiClockWise)

                fabRed.isClickable
                fabWhite.isClickable
                fabBlue.isClickable
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

        /*buttonAdd.setOnClickListener {
            val action =
                HomeFragmentDirections.actionAddVino()
            Navigation.findNavController(it).navigate(action)
        }

        buttonFav.setOnClickListener {
            val action =
                HomeFragmentDirections.actionfav()
            Navigation.findNavController(it).navigate(action)
        }*/

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
