package com.gadium.damdioniso.ui


import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.gadium.damdioniso.R
import com.gadium.damdioniso.room.Vino
import com.gadium.damdioniso.room.VinoDatabase
import kotlinx.android.synthetic.main.fragment_add_vino.*
import kotlinx.coroutines.launch
import com.gadium.damdioniso.utilities.FiltradoMinMax
import java.util.*

/**
 * Clase que gestiona el Fragment desde el que añadimos o modificamos un vino
 */
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

        //Seteamos el título de la barra superior
        (activity as AppCompatActivity).supportActionBar?.title ="Crea tu vino"

        //Mostramos los valores del spinner del tipo de vino
        val adapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.tipo_vino, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //Estos datos se muestran en la vista cuando pinchamos en un vino de la lista
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
            if (vino?.crianza != null){
                editTextCrianza.setText(vino?.crianza)
            } else { //De esta manera vamos a evitar que en una primera entrada de datos podamos introducir un año superior al actual
                editTextCrianza.filters = arrayOf(FiltradoMinMax(1,Calendar.getInstance().get(Calendar.YEAR)))
            }
            editText2Uvas.setText(vino?.uvas)
            editText4Alcohol.setText(vino?.alcohol)
            editTextDenominacion.setText(vino?.denominacion)
            editText5Envejecimiento.setText(vino?.envejecimiento)
            editText6Pais.setText(vino?.paisOrigen)
            editText7Precio.setText(vino?.precio)
            editText8Cata.setText(vino?.notasCata)
            spinner.setSelection(tipoVino)
            if (vino?.favorito === true){
                checkBox.isChecked = true
            }

        }

        //Recogemos y salvamos los datos introducidos
        buttonSave.setOnClickListener {
            val vinoNombre = editTextNombre.text.toString().trim()
            val vinoBodega = editTextBodega.text.toString().trim()
            val vinoCrianza = editTextCrianza.text.toString().trim()
            val vinoTipo = spinner.selectedItem.toString().trim()
            val vinoUvas = editText2Uvas.text.toString().trim()
            val vinoDenominacion = editTextDenominacion.text.toString().trim()
            val vinoAlcohol = editText4Alcohol.text.toString().trim()
            val vinoEnvejecimiento = editText5Envejecimiento.text.toString().trim()
            val vinoPaisOrigen = editText6Pais.text.toString().trim()
            val vinoPrecio = editText7Precio.text.toString().trim()
            val vinoNotasCata = editText8Cata.text.toString().trim()
            val vinoFavorito = checkBox.isChecked

            //No podremos guardar un vino si no tiene nombre. Lo controlamos con este if
            if(vinoNombre.isNullOrEmpty()){
                editTextNombre.error="Nombre requerido"
                return@setOnClickListener
            }

            //No podremos introducir un porcentaje de alcohol superior al 100%. Lo controlamos con este if
            if(vinoAlcohol.isNotEmpty() && vinoAlcohol.toFloat()>100.0){
                editText4Alcohol.error="Menos de 100"
                return@setOnClickListener
            }

            //No podremos introducir un año de cosecha superior al año actual. Lo controlamos comparándolo con el año actual
            if(vinoCrianza.isNotEmpty() && vinoCrianza.toInt()>(Calendar.getInstance().get(Calendar.YEAR))){
                editTextCrianza.error="El máximo es " + Calendar.getInstance().get(Calendar.YEAR)
                return@setOnClickListener
            }

            //Creamos el objeto vino con sus atributos
            val newVino = Vino(vinoNombre, vinoBodega, vinoDenominacion, vinoCrianza, vinoTipo, vinoUvas, vinoAlcohol, vinoEnvejecimiento, vinoPaisOrigen, vinoPrecio, vinoNotasCata, vinoFavorito)

            // Creamos un vino nuevo o actualizamos uno ya existente
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



    /**
     * Actualizamos un vino recibido por parámetro
     */
    private suspend fun updateVino (newVino: Vino) {
        newVino.id = vino!!.id
        context?.let {
            VinoDatabase(it).getVinoDao().updateVino(newVino)
            Toast.makeText(it, "Vino actualizado", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Guardamos un vino nuevo recibido por parámetro
     */
    private suspend fun saveVino(vino: Vino) {
        context?.let {
            VinoDatabase(it).getVinoDao().addVino(vino)
            Toast.makeText(it, "Vino añadido", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Función con la que regresamos al HomeFragment tras guardar o actualizar un vino
     */
    private fun navigateBack() {
        val action:NavDirections =
            AddVinoFragmentDirections.actionSaveVino()
        Navigation.findNavController(buttonSave).navigate(action)
    }

    /**
     * Función con la que creamos el menú del action bar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.vino_menu, menu)
    }

    /**
     * Función con la que añadimos el icono de eliminar vino en el action bar
     */
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

    /**
     * Función con la que eliminamos el vino que estamos visualizando
     */
    private fun deleteVino() {
        context?.let{
            AlertDialog.Builder(it).apply {
                setTitle("¿Seguro que quiere eliminar el vino?")
                setMessage("No podrá volver atrás")
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
