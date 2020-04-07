package projetos.danilo.mytasks.repository

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import projetos.danilo.mytasks.model.ListaTarefa
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.persistencia.GerenciadorTarefaRepository

class TarefasRepository(
    @SerializedName("gerenciadorArmazenamento")
    private val gerenciadorArmazenamento: GerenciadorTarefaRepository
) {
    private var tarefasLista: MutableList<Tarefa> = ArrayList()

    suspend fun consultarTarefas(): MutableList<Tarefa> {
        if(tarefasLista.isNotEmpty()){
            return tarefasLista
        }

        withContext(Dispatchers.IO){
            tarefasLista = gerenciadorArmazenamento.getAllTarefas()
        }
        return tarefasLista
    }

    suspend fun salvarTarefaSQLite(listaTarefa: MutableList<Tarefa>){
        salvarExcluir(tarefasLista)
    }

    private suspend fun salvarExcluir(tarefasLista: MutableList<Tarefa>) {
        val listaTarefa = ListaTarefa(tarefasLista)
        withContext(Dispatchers.IO){
            gerenciadorArmazenamento.deletar(CHAVE_TAREFAS)
            gerenciadorArmazenamento.put(CHAVE_TAREFAS, listaTarefa)
        }
    }

    private suspend fun getAllTarefas(tarefasLista: MutableList<Tarefa>) {
        val listaTarefa = ListaTarefa(tarefasLista)
        withContext(Dispatchers.IO){
//            gerenciadorArmazenamento.getAllTarefas()
        }
    }

    companion object {
        val CHAVE_TAREFAS = "TAREFAS"

        val STORAGE_TAREFAS = "storage_tarefas"
    }
}