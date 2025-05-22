package com.example.personaltasks.controller

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityCadastroBinding
import com.example.personaltasks.model.Tarefa
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private var dataSelecionada: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun configurarDatePicker() {
        binding.etDataLimite.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                dataSelecionada = LocalDate.of(year, month + 1, dayOfMonth)
                binding.etDataLimite.setText(
                    dataSelecionada?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                )
            }, ano, mes, dia)

            datePicker.show()
        }
    }

}