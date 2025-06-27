package com.example.personaltasks.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityLoginBinding
import com.example.personaltasks.helper.FirebaseAuthHelper
import com.google.firebase.auth.FirebaseAuth

/**
 * Tela de autenticação de usuário.
 * Permite login, registro e recuperação de senha com e-mail e senha via FirebaseAuth.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authHelper: FirebaseAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authHelper = FirebaseAuthHelper()

        // Verifica se o usuário já está logado
        authHelper.getCurrentUser()?.let {
            abrirTelaPrincipal()
        }

        // Ação de login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                authHelper.login(email, senha) { sucesso, erro ->
                    if (sucesso) {
                        abrirTelaPrincipal()
                    } else {
                        Toast.makeText(this, erro ?: "Erro ao logar", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação de registro
        binding.btnRegistrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                authHelper.register(email, senha) { sucesso, erro ->
                    if (sucesso) {
                        abrirTelaPrincipal()
                    } else {
                        Toast.makeText(this, erro ?: "Erro ao registrar", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação de recuperação de senha
        binding.resetPasswordBt.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Email de redefinição enviado.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Digite seu email para redefinir a senha.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Redireciona o usuário para a tela principal do app.
     */
    private fun abrirTelaPrincipal() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}