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
import kotlin.math.sqrt

class part1_calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_part1_calculator)

        //введення значень
        val inputVoltage: EditText = findViewById(R.id.input_voltage)
        val inputCurrent: EditText = findViewById(R.id.input_current)
        val inputFicTime: EditText = findViewById(R.id.input_fic_time)
        val inputLoad: EditText = findViewById(R.id.input_load)
        val inputTime: EditText = findViewById(R.id.input_time)

        //контрольний приклад
        inputVoltage.setText("10")
        inputCurrent.setText("2.5")
        inputFicTime.setText("2.5")
        inputLoad.setText("1300")
        inputTime.setText("4000")

        //результати обчислень
        val resultCurrentNormal: TextView = findViewById(R.id.result_current_normal)
        val resultCurrentEmerg: TextView = findViewById(R.id.result_current_emerg)
        val resultSectionEconom: TextView = findViewById(R.id.result_section_econom)
        val resultSectionMin: TextView = findViewById(R.id.result_section_min)

        //кнопки обчислення та повернення назад
        val backButton: Button = findViewById(R.id.button_back)
        val sumButton: Button = findViewById(R.id.button_calculate)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sumButton.setOnClickListener {
            val valueVoltage = inputVoltage.text.toString()
            val valueCurrent = inputCurrent.text.toString()
            val valueFicTime = inputFicTime.text.toString()
            val valueLoad = inputLoad.text.toString()
            val valueTime = inputTime.text.toString()

            // перевірка та наявність значень
            if (valueVoltage.isNotEmpty() && valueCurrent.isNotEmpty() && valueFicTime.isNotEmpty()
                && valueLoad.isNotEmpty() && valueTime.isNotEmpty()) {
                try {
                    val valueVoltage_f = valueVoltage.toFloat()
                    val valueCurrent_f = valueCurrent.toFloat()
                    val valueFicTime_f = valueFicTime.toFloat()
                    val valueLoad_f = valueLoad.toFloat()
                    val valueTime_f = valueTime.toFloat()

                    val Im = (valueLoad_f/2)/(sqrt(3.0)*valueVoltage_f)
                    val Impa = Im*2

                    var jEc = 0.0
                    if (valueTime_f >= 1000 && valueTime_f <= 3000){
                        jEc = 1.6
                    } else if (valueTime_f > 3000 && valueTime_f <= 5000) {
                        jEc = 1.4
                    } else if (valueTime_f > 5000){
                        jEc = 1.2
                    }

                    val cT = 92

                    val sEconom = Im/jEc
                    val sMin = (valueCurrent_f*1000* sqrt(valueFicTime_f))/cT

                    resultCurrentNormal.text = String.format(Locale.US, "%.2f", Im)
                    resultCurrentEmerg.text = String.format(Locale.US, "%.2f", Impa)
                    resultSectionEconom.text = String.format(Locale.US, "%.2f", sEconom)
                    resultSectionMin.text = String.format(Locale.US, "%.2f", sMin)


                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Введіть коректні числа.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Всі поля повинні бути заповненні.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}