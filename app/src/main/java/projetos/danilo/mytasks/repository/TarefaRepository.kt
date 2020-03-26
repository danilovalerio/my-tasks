package projetos.danilo.mytasks.repository

import androidx.lifecycle.LiveData
import projetos.danilo.mytasks.model.Tarefa

interface TarefaRepository {
    fun save(tarefa: Tarefa)
    fun remove(vararg tarefas: Tarefa)
    fun tarefaById(id: Long) : LiveData<Tarefa>
    fun search(term: String): LiveData <List<Tarefa>>
}