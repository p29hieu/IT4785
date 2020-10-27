package com.example.it4785

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.it4785.screens.Calculator.CalculatorActivity
import com.example.it4785.screens.ChangeMoney.ChangeMoneyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("TAG", actionBar?.title.toString())
        btn_go_to_calculator.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, CalculatorActivity::class.java)
            startActivity(intent)
        }

        btn_go_to_change_money.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, ChangeMoneyActivity::class.java)
            startActivity(intent)
        }
    }
}