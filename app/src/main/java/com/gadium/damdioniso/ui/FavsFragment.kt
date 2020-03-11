package com.gadium.damdioniso.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_favs.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
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
