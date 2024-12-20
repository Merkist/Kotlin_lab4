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

class part3_calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_part3_calculator)

        //введення значень
        val inputResistanceNormR: EditText = findViewById(R.id.input_resistence1_norm)
        val inputResistanceNormX: EditText = findViewById(R.id.input_resistence2_norm)
        val inputResistanceMinR: EditText = findViewById(R.id.input_resistence1_min)
        val inputResistanceMinX: EditText = findViewById(R.id.input_resistence2_min)

        //контрольний приклад
        inputResistanceNormR.setText("10.65")
        inputResistanceNormX.setText("24.02")
        inputResistanceMinR.setText("34.88")
        inputResistanceMinX.setText("65.68")

        //результати обчислень
        val resultCurrent3Norm110: TextView = findViewById(R.id.result_current_3_norm_110)
        val resultCurrent2Norm110: TextView = findViewById(R.id.result_current_2_norm_110)
        val resultCurrent3Min110: TextView = findViewById(R.id.result_current_3_min_110)
        val resultCurrent2Min110: TextView = findViewById(R.id.result_current_2_min_110)
        val resultCurrent3NormReal: TextView = findViewById(R.id.result_current_3_norm_real)
        val resultCurrent2NormReal: TextView = findViewById(R.id.result_current_2_norm_real)
        val resultCurrent3MinReal: TextView = findViewById(R.id.result_current_3_min_real)
        val resultCurrent2MinReal: TextView = findViewById(R.id.result_current_2_min_real)

        //кнопки обчислення та повернення назад
        val backButton: Button = findViewById(R.id.button_back3)
        val sumButton: Button = findViewById(R.id.button_calculate3)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sumButton.setOnClickListener {
            val valueResistanceNormR = inputResistanceNormR.text.toString()
            val valueResistanceNormX = inputResistanceNormX.text.toString()
            val valueResistanceMinR = inputResistanceMinR.text.toString()
            val valueResistanceMinX = inputResistanceMinX.text.toString()

            // перевірка та наявність значень
            if (valueResistanceNormR.isNotEmpty() && valueResistanceNormX.isNotEmpty() &&
                valueResistanceMinR.isNotEmpty() && valueResistanceMinX.isNotEmpty()) {
                try {
                    val valueResistanceNormR_f = valueResistanceNormR.toFloat()
                    val valueResistanceNormX_f = valueResistanceNormX.toFloat()
                    val valueResistanceMinR_f = valueResistanceMinR.toFloat()
                    val valueResistanceMinX_f = valueResistanceMinX.toFloat()

                    val Ukmax = 11.1
                    val Uvn = 115.0
                    val SnomT = 6.3

                    val xT = (Ukmax*Uvn*Uvn)/(100*SnomT)
                    val xH =valueResistanceNormX_f + xT
                    val zH = sqrt(valueResistanceNormR_f.pow(2) + xH.pow(2))
                    val xHMin =valueResistanceMinX_f + xT
                    val zHMin = sqrt(valueResistanceMinR_f.pow(2) + xHMin.pow(2))

                    val iH3 = (Uvn*1000)/(sqrt(3.0)*zH)
                    val iH2 = iH3 * (sqrt(3.0)/2)
                    val iH3Min = (Uvn*1000)/(sqrt(3.0)*zHMin)
                    val iH2Min = iH3Min * (sqrt(3.0)/2)

                    val Unn = 11.0
                    val k = Unn.pow(2)/Uvn.pow(2)

                    val rHN = valueResistanceNormR_f * k
                    val xHN = xH * k
                    val zHN = sqrt(rHN.pow(2) + xHN.pow(2))
                    val rHNMin = valueResistanceMinR_f * k
                    val xHNMin = xHMin * k
                    val zHNMin = sqrt(rHNMin.pow(2) + xHNMin.pow(2))

                    val iHN3 = (Unn*1000)/(sqrt(3.0)*zHN)
                    val iHN2 = iHN3 * (sqrt(3.0)/2)
                    val iHN3Min = (Unn*1000)/(sqrt(3.0)*zHNMin)
                    val iHN2Min = iHN3Min * (sqrt(3.0)/2)

                    resultCurrent3Norm110.text = String.format(Locale.US, "%.2f", iH3)
                    resultCurrent2Norm110.text = String.format(Locale.US, "%.2f", iH2)
                    resultCurrent3Min110.text = String.format(Locale.US, "%.2f", iH3Min)
                    resultCurrent2Min110.text = String.format(Locale.US, "%.2f", iH2Min)
                    resultCurrent3NormReal.text = String.format(Locale.US, "%.2f", iHN3)
                    resultCurrent2NormReal.text = String.format(Locale.US, "%.2f", iHN2)
                    resultCurrent3MinReal.text = String.format(Locale.US, "%.2f", iHN3Min)
                    resultCurrent2MinReal.text = String.format(Locale.US, "%.2f", iHN2Min)

                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Введіть коректні числа.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Всі поля повинні бути заповненні.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}