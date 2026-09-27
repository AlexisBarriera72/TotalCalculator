package com.alexisbarrierapacheco.totalcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    //Variables usadas para referenciar objetos del UI
    private var billAmount = 0.0 // Cantidad ingresada por el usuario
    private lateinit var amountTextView: TextView // Muestra la cantidad con formato
    private lateinit var taxTextView: TextView // Muestra el tax calculado
    private lateinit var totalTextView: TextView // Muestra el total calculado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias para manipular en el programa los Views
        amountTextView = findViewById(R.id.amountTextView)
        taxTextView = findViewById(R.id.taxTextView)
        totalTextView = findViewById(R.id.totalTextView)
        taxTextView.text = currencyFormat.format(0)
        totalTextView.text = currencyFormat.format(0)

        // Asigna amountEditText's TextWatcher (eventos ante cambios en el monto de la factura)
        val amountEditText: EditText = findViewById(R.id.amountEditText)
        amountEditText.addTextChangedListener(amountEditTextWatcher)
    } //end OnCreate

    // calcula y muestra el tax y el total
    private fun calculate() {
        // calcula el tax y el total
        val tax = billAmount * 0.115
        val total = billAmount + tax
        //Mostrar el tax y el total en forma formato currency
        taxTextView.text = currencyFormat.format(tax)
        totalTextView.text = currencyFormat.format(total)
    } //end Calculate

    //Objeto listener para el EditText's eventos ante cambios en el texto (text-changed events)
    //Implementacion de la Interface TextWatcher es llamado cuando el usuario modifica el bill amount
    private val amountEditTextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int,
                                   before: Int, count: Int) {

            try { //Obtener el monto de la factura y mostrar el valor con formato contable
                billAmount = s.toString().toDouble() / 100.0
                amountTextView.text = currencyFormat.format(billAmount)
            }
            catch (e: NumberFormatException) { //Si s esta vacio formato incorrecto
                amountTextView.text = ""
                billAmount = 0.0
            }
            calculate() //llamada a la funcion que actualiza el tax y el total en los TextView's
        }
        override fun afterTextChanged(s: Editable?) { }
        override fun beforeTextChanged(
            s: CharSequence?, start: Int, count: Int, after: Int) { }
    }

    companion object {
        // Formato de salida
        private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    }
} //end class MainActivity