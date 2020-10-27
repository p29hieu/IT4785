package com.example.it4785.screens.Calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.it4785.R
import com.example.it4785.helpers.Utils
import kotlinx.android.synthetic.main.activity_calculator.*
import java.lang.Error
import kotlin.math.round

class CalculatorActivity : AppCompatActivity() {
    private var textStr: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        btn_ce.setOnClickListener {
            onClickBtnDeleteAll()
        }
        btn_delete.setOnClickListener {
            onClickBtnDeleteOne()
        }
        btn_delete.setOnLongClickListener() {
            onClickBtnDeleteAll()
            return@setOnLongClickListener true
        }

        btn_plus_and_sub.setOnClickListener {
            onClickBtnPlusAndSub()
        }
        btn_dot.setOnClickListener {
//            onClickBtnDot()
        }

        btn_plus.setOnClickListener {
            onClickBtnPlus()
        }

        btn_sub.setOnClickListener {
            onClickBtnSub()
        }

        btn_mul.setOnClickListener {
            onClickBtnMul()
        }

        btn_div.setOnClickListener {
            onClickBtnDiv()
        }

        btn_equal.setOnClickListener {
            onClickBtnEqual()
        }

        btn_square_root.setOnClickListener {
            onClickBtnSquareRoot()
        }
    }

    override fun onResume() {
        super.onResume()
        edit_text_input.showSoftInputOnFocus = false
        edit_text_input.isFocusable = true
        edit_text_input.setSelection(1, 1)
    }

    private fun onClickBtnDeleteAll() {
        edit_text_input.text = Editable.Factory.getInstance().newEditable("0")
        edit_text_input.isFocusable = true
        edit_text_input.setSelection(1, 1)
    }

    private fun onClickBtnDeleteOne() {
        val selectionEnd = edit_text_input.selectionEnd
        if (edit_text_input.text.length == 1) {
            onClickBtnDeleteAll()
            return
        }
        if (selectionEnd == 0) return
        val selectionStart = edit_text_input.selectionStart
        textStr = edit_text_input.text.toString()
        edit_text_input.text = Editable.Factory.getInstance()
            .newEditable(
                textStr.substring(0, selectionStart - 1) + textStr.substring(
                    selectionEnd
                )
            )
        edit_text_input.setSelection(selectionStart - 1)
    }

    @SuppressLint("ResourceType")
    private fun onClickBtnChangeTextView(str: String) {
        val textSize =
            this.getString(R.dimen.text_size_edit_text_main).replace("sp", "").toFloat()
        val length = edit_text_input.text.length
        when {
            length < 8 -> {
                edit_text_input.textSize =
                    textSize.toFloat()
            }
            length < 12 -> edit_text_input.textSize =
                (textSize * 0.7).toFloat()
            else -> edit_text_input.textSize =
                (textSize * 0.5).toFloat()
        }
        val selectionEnd = edit_text_input.selectionEnd
        val selectionStart = edit_text_input.selectionStart
        textStr = edit_text_input.text.toString()
        edit_text_input.text = Editable.Factory.getInstance().newEditable(
            textStr.substring(
                0,
                selectionStart
            ) + str + textStr.substring(selectionEnd, textStr.length)
        )

        textStr = edit_text_input.text.toString()
        when {
            "^(0\\d)".toRegex().matches(textStr) -> {
                edit_text_input.text = Editable.Factory.getInstance().newEditable(textStr.substring(1))
                edit_text_input.setSelection(selectionStart)
            }
            "^(-0\\d)".toRegex().matches(textStr) -> {
                edit_text_input.text = Editable.Factory.getInstance().newEditable(textStr.substring(2))
                edit_text_input.setSelection(if (selectionStart - 1 < 0) 0 else selectionStart - 1)
            }
            else -> edit_text_input.setSelection(if (selectionStart + str.length > textStr.length) textStr.length else selectionStart + str.length)
        }
    }

    fun onClickNumber(view: View) {
        val numberStr = findViewById<Button>(view.id).text.toString()
        onClickBtnChangeTextView(numberStr)
    }

    private fun onClickBtnPlusAndSub() {
        textStr = edit_text_input.text.toString()
        if (textStr == "0") {
            return
        }
        edit_text_input.text = Editable.Factory.getInstance().newEditable(
            (textStr.toFloat() * -1).toString()
        )
    }

//    private fun onClickBtnDot() {
//        textStr = edit_text_input.text.toString()
//        if ("/./gi".toRegex().matches(textStr)) {
//            return
//        }
//        if (edit_text_input.selectionEnd == 1 && textStr == "0") {
//            edit_text_input.text = Editable.Factory.getInstance().newEditable("0.")
//            edit_text_input.setSelection(2)
//            return
//        }
//        onClickBtnChangeTextView(".")
//    }

    private fun onClickBtnPlus() {
//        val lastChar = edit_text_input.text.toString()
        onClickBtnChangeTextView("+")
    }

    private fun onClickBtnSub() {
//        val lastChar = edit_text_input.text.toString()
        onClickBtnChangeTextView("-")
    }

    private fun onClickBtnMul() {
//        val lastChar = edit_text_input.text.toString()
        onClickBtnChangeTextView("×")
    }

    private fun onClickBtnDiv() {
//        val lastChar = edit_text_input.text.toString()
        onClickBtnChangeTextView("/")
    }

    private fun onClickBtnSquareRoot() {
        val currentSelection = edit_text_input.selectionStart
        onClickBtnChangeTextView("√()")
        edit_text_input.setSelection(currentSelection + 2)

    }

    @SuppressLint("ResourceType")
    private fun onClickBtnEqual() {
        var strInput = edit_text_input.text.toString()
        strInput = strInput.replace('×', '*').replace("√", "sqrt").trim()
        var result = 0.0
        try {
            result = Utils().eval(strInput)
        } catch (e: Error) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        val resultStr: String
        resultStr = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else round(result * 10e6).div(10e6).toString()
        edit_text_input.text = Editable.Factory.getInstance().newEditable(
            resultStr
        )
        edit_text_input.textSize =
            this.getString(R.dimen.text_size_edit_text_main_equal).replace("d", "").replace("s", "")
                .replace("p", "").toFloat()
        edit_text_input.setSelection(edit_text_input.length())
    }
}