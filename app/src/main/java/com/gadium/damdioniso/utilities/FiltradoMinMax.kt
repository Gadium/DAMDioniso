package com.gadium.damdioniso.utilities

import android.text.InputFilter
import android.text.Spanned
import java.lang.NumberFormatException

/**
 * Clase con la que vamos a controlar que con el teclado podamos introducir un número o no. Según usemos un constructor u otro pasaremos dos enteros o dos cadenas que
 * se convertirán a enteros.
 */
class FiltradoMinMax: InputFilter {

    private var min:Int = 0
    private var max:Int = 0

    constructor(min:Int, max:Int) {
        this.min = min
        this.max = max
    }

    constructor(min: String, max: String) {
        this.min = Integer.parseInt(min)
        this.max = Integer.parseInt(max)
    }

    override fun filter(source:CharSequence, start:Int, end:Int, dest: Spanned, dstart:Int, dend:Int): CharSequence? {
        try
        {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (enRango(min, max, input))
                return null
        }
        catch (nfe:NumberFormatException) {}
        return ""
    }
    private fun enRango(a:Int, b:Int, c:Int):Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}