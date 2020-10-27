package com.example.it4785.screens.ChangeMoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.it4785.R
import kotlinx.android.synthetic.main.activity_change_money.*
import kotlin.math.roundToInt

class ChangeMoneyActivity : AppCompatActivity() {
    lateinit var from: Currency
    lateinit var to: Currency
    var amount: Double = 0.0
    val listCurrency: List<Currency> = listOf(
        Currency("US dollar", "USD", 1.1819),
        Currency("Japanese yen", "JPY", 124.14),
        Currency("Bulgarian lev", "BGN", 1.9558),
        Currency("Czech koruna", "CZK", 27.295),
        Currency("Danish krone", "DKK", 7.4412),
        Currency("Pound sterling", "GBP", 0.90755),
        Currency("Hungarian forint", "HUF", 365.02),
        Currency("Polish zloty", "PLN", 4.5799),
        Currency("Singapore dollar", "SGD", 1.6095),
        Currency("Chinese yuan renminbi", "CNY", 7.9342),
        Currency("Euror", "EUR", 1.0),
        Currency("Brazilian real", "BRL", 6.6714),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_money)
        from = listCurrency[0]
        to = listCurrency[0]
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listCurrency.map {
                "${it.name} (${it.code})"
            })
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        from_spinner.adapter = dataAdapter
        from_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                from = listCurrency[position]
                printResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        to_spinner.adapter = dataAdapter
        to_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                to = listCurrency[position]
                printResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        edit_text_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                printResult()
            }

        })

        btn_clear.setOnClickListener {
            text_result.text = null
            edit_text_amount.text = null
        }
    }

    fun printResult() {
        val text: String = edit_text_amount.text.toString()
        if (text.isNotEmpty()) {
            this.amount = text.toDouble()
            val resultValue: Double = (this.amount * from.rate / to.rate)
            val result = "${((amount * 1E6).roundToInt()/1E6).toString()} ${from.code} = ${((resultValue * 1E6).roundToInt()/1E6).toString()} ${to.code}"
            text_result.text = Editable.Factory.getInstance().newEditable(result)
        }
    }
}