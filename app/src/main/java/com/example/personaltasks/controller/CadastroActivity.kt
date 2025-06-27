package com.example.personaltasks.controller

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.R
import com.example.personaltasks.databinding.ActivityCadastroBinding
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRemoteRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Activity responsável pelo cadastro, edição e visualização de uma Tarefa.
 * Utiliza ViewBinding para acessar as Views.
 */
class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private var dataSelecionada: LocalDate? = null
    private val repository = TarefaRemoteRepository()
    private var tarefa: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val opcoes = resources.getStringArray(R.array.opcoes_prioridade)
        val adapterPrioridade = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoes)
        adapterPrioridade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrioridade.adapter = adapterPrioridade

        tarefa = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("tarefa", Tarefa::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("tarefa")
        }

        tarefa?.let {
            binding.etTitulo.setText(it.titulo)
            binding.etDescricao.setText(it.descricao)
            binding.etDataLimite.setText(it.dataLimite.format(DateTimeFormatter.ISO_LOCAL_DATE))
            binding.cbCumprida.isChecked = it.cumprida
            dataSelecionada = it.dataLimite

            val index = opcoes.indexOf(it.prioridade)
            if (index >= 0) binding.spinnerPrioridade.setSelection(index)
        }

        configurarDatePicker()

        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun configurarDatePicker() {
        binding.etDataLimite.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                dataSelecionada = LocalDate.of(year, month + 1, dayOfMonth)
                binding.etDataLimite.setText(dataSelecionada?.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }, ano, mes, dia)

            datePicker.show()
        }
    }

    private fun salvarTarefa() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val dataLimite = dataSelecionada ?: LocalDate.now()
        val prioridadeSelecionada = binding.spinnerPrioridade.selectedItem.toString()

        val novaTarefa = Tarefa(
            id = tarefa?.id ?: "",
            titulo = titulo,
            descricao = descricao,
            dataLimite = dataLimite,
            cumprida = binding.cbCumprida.isChecked,
            excluida = false,
            prioridade = prioridadeSelecionada
        )

        if (tarefa == null) {
            repository.insert(novaTarefa)
        } else {
            repository.update(novaTarefa)
        }

        finish()
    }
}
