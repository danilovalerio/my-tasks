package projetos.danilo.mytasks.data

import projetos.danilo.mytasks.model.Tarefa

interface Repository {
    suspend fun getListTarefa(): List<Tarefa>
}

class RepositoryImpl(
    private val cacheService: TarefaCacheService
): Repository {
    override suspend fun getListTarefa(): List<Tarefa> {
        return cacheService.getTarefas()
    }
}