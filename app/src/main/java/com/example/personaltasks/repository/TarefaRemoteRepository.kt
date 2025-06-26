package com.example.personaltasks.repository

import com.example.personaltasks.helper.TarefaFirebaseMapper
import com.example.personaltasks.model.Tarefa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TarefaRemoteRepository {

    private val auth = FirebaseAuth.getInstance()

    // Referência base para o nó de tarefas do usuário
    private val database: DatabaseReference
        get() = FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(auth.currentUser?.uid ?: "anonimo")
            .child("tarefas")

    /**
     * Observa todas as tarefas (ativas e excluídas).
     * Ideal para debug ou admin.
     */
    fun observeAll(callback: (List<Tarefa>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tarefas = mutableListOf<Tarefa>()
                snapshot.children.forEach { tarefaSnapshot ->
                    val tarefa = tarefaSnapshot.getValue(Tarefa::class.java)
                    tarefa?.let {
                        it.id = tarefaSnapshot.key ?: ""
                        tarefas.add(it)
                    }
                }
                callback(tarefas)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    /**
     * Busca somente as tarefas ativas (excluida = false).
     */
    fun getAtivas(callback: (List<Tarefa>) -> Unit) {
        database.orderByChild("excluida").equalTo(false)
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

    /**
     * Busca somente as tarefas excluídas (excluida = true).
     */
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

    /**
     * Insere uma nova tarefa com ID gerado automaticamente.
     */
    fun insert(tarefa: Tarefa) {
        val novaRef = database.push()
        tarefa.id = novaRef.key!!
        val map = TarefaFirebaseMapper.toMap(tarefa)
        novaRef.setValue(map)
    }

    /**
     * Atualiza uma tarefa existente.
     */
    fun update(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            val map = TarefaFirebaseMapper.toMap(tarefa)
            database.child(tarefa.id).setValue(map)
        }
    }

    /**
     * Marca uma tarefa como excluída (soft delete).
     */
    fun delete(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            tarefa.excluida = true
            val map = TarefaFirebaseMapper.toMap(tarefa)
            database.child(tarefa.id).setValue(map)
        }
    }

    /**
     * Reativa uma tarefa excluída (excluida = false).
     */
    fun reativar(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            tarefa.excluida = false
            val map = TarefaFirebaseMapper.toMap(tarefa)
            database.child(tarefa.id).setValue(map)
        }
    }
}