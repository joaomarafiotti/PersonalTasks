package com.example.personaltasks.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Classe helper para centralizar operações de autenticação com FirebaseAuth.
 */
class FirebaseAuthHelper {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Cria uma nova conta com e-mail e senha.
     */
    fun register(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /**
     * Realiza login com e-mail e senha.
     */
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /**
     * Retorna o usuário logado ou null.
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    /**
     * Realiza logout do Firebase.
     */
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}