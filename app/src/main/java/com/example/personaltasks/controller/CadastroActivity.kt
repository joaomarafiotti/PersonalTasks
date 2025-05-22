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

        configurarDatePicker()

        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    /**
     * Configura o DatePicker para selecionar a data limite.
     */
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

    /**
     * Cria e retorna uma Tarefa com os dados preenchidos.
     */
    private fun salvarTarefa() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val dataLimite = dataSelecionada ?: LocalDate.now()

        val tarefa = Tarefa(
            titulo = titulo,
            descricao = descricao,
            dataLimite = dataLimite
        )

        // Futuramente pode passar a Tarefa de volta via Intent ou salvar no banco
        // Por enquanto só finaliza
        finish()
    }
}