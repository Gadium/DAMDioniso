package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_vino, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.tipo_vino, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        arguments?.let {
            vino = AddVinoFragmentArgs.fromBundle(it).vino
            var tipoVino: Int = 0

            if(
                vino?.tipo.equals("Tinto")
            ) tipoVino = 0
            if(
                vino?.tipo.equals("Rosado")
            ) tipoVino = 1
            if(
                vino?.tipo.equals("Blanco")
            ) tipoVino = 2
            if(
                vino?.tipo.equals("Azul")
            ) tipoVino = 3
            if(
                vino?.tipo.equals("Espumoso")
            ) tipoVino = 4

            editTextNombre.setText(vino?.nombre)
            editTextBodega.setText(vino?.bodega)
            editTextCrianza.setText(vino?.crianza)
            editText2Uvas.setText(vino?.uvas)
            editText4Alcohol.setText(vino?.alcohol)
            editText5Envejecimiento.setText(vino?.envejecimiento)
            editText6Pais.setText(vino?.paisOrigen)
            editText7Precio.setText(vino?.precio)
            editText8Cata.setText(vino?.notasCata)
            spinner.setSelection(tipoVino)
        }

        buttonSave.setOnClickListener {
            val vinoNombre = editTextNombre.text.toString().trim()
            val vinoBodega = editTextBodega.text.toString().trim()
            val vinoCrianza = editTextCrianza.text.toString().trim()
            val vinoTipo = spinner.selectedItem.toString().trim()
            val vinoUvas = editText2Uvas.text.toString().trim()
            val vinoAlcohol = editText4Alcohol.text.toString().trim()
            val vinoEnvejecimiento = editText5Envejecimiento.text.toString().trim()
            val vinoPaisOrigen = editText6Pais.text.toString().trim()
            val vinoPrecio = editText7Precio.text.toString().trim()
            val vinoNotasCata = editText8Cata.text.toString().trim()

            if(vinoNombre.isNullOrEmpty()){
                editTextNombre.error="Nombre requerido"
                return@setOnClickListener
            }

            val newVino = Vino(vinoNombre, vinoBodega, vinoCrianza, vinoTipo, vinoUvas, vinoAlcohol, vinoEnvejecimiento, vinoPaisOrigen, vinoPrecio, vinoNotasCata)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.vino_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteVino -> {
                vino?.let {
                    deleteVino()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteVino() {
        context?.let{
            AlertDialog.Builder(it).apply {
                setTitle("¿Seguro que quiere eliminar el vino?")
                setMessage("No podrá volver a trás")
                setPositiveButton("OK") {
                    dialog, which ->
                    launch {
                        VinoDatabase(it).getVinoDao().deleteVino(vino!!)
                        navigateBack()
                    }
                }
                setNegativeButton("Cancelar") {dialog, which ->  }
                show()
            }
        }
    }

}
