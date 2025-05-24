package com.example.personaltasks.controller

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.personaltasks.databinding.ActivityCadastroBinding
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Activity responsável pelo cadastro, edição e visualização de uma Tarefa.
 * Utiliza ViewBinding para acessar as Views.
 */
class CadastroActivity : AppCompatActivity() {

    // ViewBinding para acessar os elementos do layout
    private lateinit var binding: ActivityCadastroBinding

    // Guarda a data selecionada no DatePicker
    private var dataSelecionada: LocalDate? = null

    // Repositório de Tarefas para operações no banco
    private lateinit var repository: TarefaRepository

    // Tarefa recebida para edição, se houver
    private var tarefa: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout com ViewBinding
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o repositório
        repository = TarefaRepository(this)

        // Recupera a Tarefa enviada pela Intent, se for edição
        tarefa = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("tarefa", Tarefa::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("tarefa")
        }

        // Se estiver editando, preenche os campos com os dados da Tarefa
        tarefa?.let {
            binding.etTitulo.setText(it.titulo)
            binding.etDescricao.setText(it.descricao)
            binding.etDataLimite.setText(it.dataLimite.format(DateTimeFormatter.ISO_LOCAL_DATE))
            dataSelecionada = it.dataLimite
        }

        // Configura o DatePicker para seleção de data
        configurarDatePicker()

        // Define ação do botão Salvar
        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        // Define ação do botão Cancelar
        binding.btnCancelar.setOnClickListener {
            finish() // Fecha a tela e volta
        }
    }

    /**
     * Configura o DatePickerDialog que será exibido ao clicar no campo de data.
     */
    private fun configurarDatePicker() {
        binding.etDataLimite.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // Atualiza a data selecionada
                dataSelecionada = LocalDate.of(year, month + 1, dayOfMonth)
                binding.etDataLimite.setText(dataSelecionada?.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }, ano, mes, dia)

            datePicker.show()
        }
    }

    /**
     * Salva a Tarefa no banco de dados.
     * Se for uma nova Tarefa, insere; se for edição, atualiza.
     */
    private fun salvarTarefa() {
        val titulo = binding.etTitulo.text.toString()
        val descricao = binding.etDescricao.text.toString()
        val dataLimite = dataSelecionada ?: LocalDate.now()

        // Cria uma nova Tarefa, mantendo o ID se for edição
        val novaTarefa = Tarefa(
            id = tarefa?.id ?: 0,
            titulo = titulo,
            descricao = descricao,
            dataLimite = dataLimite
        )

        // Executa operação no banco usando coroutines
        lifecycleScope.launch {
            if (tarefa == null) {
                repository.insert(novaTarefa)
            } else {
                repository.update(novaTarefa)
            }
            finish() // Fecha a tela após salvar
        }
    }
}
