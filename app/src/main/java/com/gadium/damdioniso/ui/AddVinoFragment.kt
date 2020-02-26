package com.gadium.damdioniso.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.gadium.damdioniso.R
import kotlinx.android.synthetic.main.fragment_add_vino.*


class AddVinoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vino, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonSave.setOnClickListener {
            val action:NavDirections =
                AddVinoFragmentDirections.actionSaveVino()
            Navigation.findNavController(it).navigate(action)
        }
    }


}
