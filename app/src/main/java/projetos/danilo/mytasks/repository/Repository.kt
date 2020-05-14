package projetos.danilo.mytasks.repository

import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.repository.db.TarefaDatabase

interface Repository {
    suspend fun getListTarefa(): List<Tarefa>

    suspend fun adicionarTarefa(tarefa: Tarefa)

    suspend fun excluirPorId(id: Int): List<Tarefa>

    suspend fun alterarConclusao(id: Int, concluida: Int)

    suspend fun deletarTarefa(tarefa: Tarefa)

    suspend fun exibirOcultarConcluidas(concluida: Int): List<Tarefa>
}

class RepositoryImpl(
    private val cacheService: TarefaCacheService, private val tarefaDatabase: TarefaDatabase?
) : Repository {
    override suspend fun getListTarefa(): List<Tarefa> {
        return tarefaDatabase?.getTarefaDao()?.getAllTarefasOrdemAlfabetica() ?: listOf()
    }

    override suspend fun adicionarTarefa(tarefa: Tarefa) {
        return tarefaDatabase?.getTarefaDao()?.adicionarTarefa(tarefa)!!
    }

    override suspend fun excluirPorId(id: Int): List<Tarefa> {
        tarefaDatabase?.getTarefaDao()?.excluirPorId(id)
        return tarefaDatabase?.getTarefaDao()?.getAllTarefas() ?: listOf()
    }

    override suspend fun alterarConclusao(id: Int, concluida: Int) {
        tarefaDatabase?.getTarefaDao()?.alterarConclusao(id, concluida)
    }

    override suspend fun deletarTarefa(tarefa: Tarefa) {
        tarefaDatabase?.getTarefaDao()?.deletarTarefa(tarefa)
    }

    override suspend fun exibirOcultarConcluidas(concluida: Int): List<Tarefa> {
        return tarefaDatabase?.getTarefaDao()?.exibirOcultarConcluidas(concluida) ?: listOf()
    }
}