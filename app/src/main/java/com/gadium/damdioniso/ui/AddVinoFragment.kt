package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.Vino
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_add_vino.*
import kotlinx.coroutines.launch


class AddVinoFragment : BaseFragment() {

    private var vino: Vino? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vino, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            vino = AddVinoFragmentArgs.fromBundle(it).vino
            editTextNombre.setText(vino?.nombre)
            editTextBodega.setText(vino?.bodega)
            editTextCrianza.setText(vino?.crianza)
        }

        buttonSave.setOnClickListener {
            val vinoNombre = editTextNombre.text.toString().trim()
            val vinoBodega = editTextBodega.text.toString().trim()
            val vinoCrianza = editTextCrianza.text.toString().trim()

            if(vinoNombre.isNullOrEmpty()){
                editTextNombre.error="Nombre requerido"
                return@setOnClickListener
            }

            val newVino = Vino(vinoNombre, vinoBodega, vinoCrianza)

            launch {
                if(vino == null){
                    saveVino(newVino)
                } else {
                    updateVino(newVino)
                }
            }
            navigateBack()
        }
    }

    private suspend fun updateVino (newVino: Vino) {
        newVino.id = vino!!.id
        context?.let {
            VinoDatabase(it).getVinoDao().updateVino(newVino)
            Toast.makeText(it, "Vino actualizado", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun saveVino(vino: Vino) {
        context?.let {
            VinoDatabase(it).getVinoDao().addVino(vino)
            Toast.makeText(it, "Vino añadido", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateBack() {
        val action:NavDirections =
            AddVinoFragmentDirections.actionSaveVino()
        Navigation.findNavController(buttonSave).navigate(action)
    }
}
