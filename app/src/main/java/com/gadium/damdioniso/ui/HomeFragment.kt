package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonAdd.setOnClickListener {
            val action =
                HomeFragmentDirections.actionAddVino()
            Navigation.findNavController(it).navigate(action)
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
