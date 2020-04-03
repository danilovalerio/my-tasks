package projetos.danilo.mytasks.repository

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import projetos.danilo.mytasks.model.ListaTarefa
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.persistencia.GerenciadorArmazenamento

class TarefasRepository(
    @SerializedName("gerenciadorArmazenamento")
    private val gerenciadorArmazenamento: GerenciadorArmazenamento) {
    private var tarefasLista: MutableList<Tarefa> = ArrayList()

    suspend fun consultarTarefasSQLite(): MutableList<Tarefa> {
        if(tarefasLista.isEmpty()){
            return tarefasLista
        }

        withContext(Dispatchers.IO){
            gerenciadorArmazenamento.get(CHAVE_TAREFAS, ListaTarefa::class.java)
                ?.listaTarefa?.toMutableList()?.let {
                    tarefasLista = it
                }
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

    companion object {
        val CHAVE_TAREFAS = "TAREFAS"

        val STORAGE_TAREFAS = "storage_tarefas"
    }
}