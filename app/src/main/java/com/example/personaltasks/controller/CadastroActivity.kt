package com.example.personaltasks.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityCadastroBinding
import com.example.personaltasks.model.Tarefa
import java.time.LocalDate

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private var dataSelecionada: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o ViewBinding
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}