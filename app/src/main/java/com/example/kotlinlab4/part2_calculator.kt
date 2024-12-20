package com.example.kotlinlab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.pow
import kotlin.math.sqrt

class part2_calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_part2_calculator)

        //введення значень
        val inputVoltage2: EditText = findViewById(R.id.input_voltage2)
        val inputPower: EditText = findViewById(R.id.input_power)

        //контрольний приклад
        inputVoltage2.setText("10.5")
        inputPower.setText("200")

        //результати обчислень
        val resultResistance: TextView = findViewById(R.id.result_resistance)
        val resultCurrent: TextView = findViewById(R.id.result_current)

        //кнопки обчислення та повернення назад
        val backButton: Button = findViewById(R.id.button_back2)
        val sumButton: Button = findViewById(R.id.button_calculate2)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sumButton.setOnClickListener {
            val valueVoltage2 = inputVoltage2.text.toString()
            val valuePower = inputPower.text.toString()

            // перевірка та наявність значень
            if (valueVoltage2.isNotEmpty() && valuePower.isNotEmpty()) {
                try {
                    val valueVoltage2_f = valueVoltage2.toFloat()
                    val valuePower_f = valuePower.toFloat()

                    val sNomT = 6.3

                    val xC = valueVoltage2_f.pow(2)/valuePower_f
                    val xT = (valueVoltage2_f/100)*(valueVoltage2_f.pow(2)/sNomT)
                    val x = xC + xT
                    val iP0 = valueVoltage2_f/(sqrt(3.0)*x)

                    resultResistance.text = String.format(Locale.US, "%.2f", x)
                    resultCurrent.text = String.format(Locale.US, "%.2f", iP0)


                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Введіть коректні числа.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Всі поля повинні бути заповненні.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}