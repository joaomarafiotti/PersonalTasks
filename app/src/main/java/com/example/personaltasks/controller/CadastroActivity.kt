package com.example.personaltasks.controller

import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityCadastroBinding
import com.example.personaltasks.model.Tarefa
import java.time.LocalDate

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private var dataSelecionada: LocalDate? = null
}