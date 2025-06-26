package com.example.personaltasks.repository

import com.example.personaltasks.helper.TarefaFirebaseMapper
import com.example.personaltasks.model.Tarefa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TarefaRemoteRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database: DatabaseReference
        get() = FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(auth.currentUser?.uid ?: "anonimo")
            .child("tarefas")

    fun observeAll(callback: (List<Tarefa>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        database.child("tarefas").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tarefas = mutableListOf<Tarefa>()
                snapshot.children.forEach { tarefaSnapshot ->
                    val tarefa = tarefaSnapshot.getValue(Tarefa::class.java)
                    tarefa?.let { tarefas.add(it) }
                }
                callback(tarefas)
            }

            override fun onCancelled(error: DatabaseError) {
                // Você pode tratar erros aqui, se quiser.
            }
        })
    }

    fun getExcluidas(callback: (List<Tarefa>) -> Unit) {
        database.orderByChild("excluida").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tarefas = mutableListOf<Tarefa>()
                    for (child in snapshot.children) {
                        val map = child.value as? Map<String, Any?> ?: continue
                        val tarefa = TarefaFirebaseMapper.fromMap(map)
                        tarefa.id = child.key!!
                        tarefas.add(tarefa)
                    }
                    callback(tarefas)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }

    fun insert(tarefa: Tarefa) {
        val novaRef = database.push()
        tarefa.id = novaRef.key!!
        val map = TarefaFirebaseMapper.toMap(tarefa)
        novaRef.setValue(map)
    }

    fun update(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            val map = TarefaFirebaseMapper.toMap(tarefa)
            database.child(tarefa.id).setValue(map)
        }
    }

    fun delete(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            tarefa.excluida = true
            val map = TarefaFirebaseMapper.toMap(tarefa)
            database.child(tarefa.id).setValue(map)
        }
    }
}