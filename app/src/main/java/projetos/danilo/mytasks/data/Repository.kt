package projetos.danilo.mytasks.data

import projetos.danilo.mytasks.data.db.TarefaDatabase
import projetos.danilo.mytasks.model.Tarefa

interface Repository {
    suspend fun getListTarefa(): List<Tarefa>
}

class RepositoryImpl(
    private val cacheService: TarefaCacheService, private val tarefaDatabase: TarefaDatabase?
): Repository {
    override suspend fun getListTarefa(): List<Tarefa> {
        return tarefaDatabase?.getTarefaDao()?.getAllTarefas() ?: cacheService.getTarefas()
    }
}