package projetos.danilo.mytasks.data

import projetos.danilo.mytasks.model.Tarefa

interface TarefaCacheService {
    fun getTarefas(): List<Tarefa>
}

class TarefasCacheServiceImpl: TarefaCacheService {
    override fun getTarefas(): List<Tarefa> {
        val tarefa1: Tarefa = Tarefa(10, "Cache 1", "Minha tarefa exemplo", null, 0)
        val tarefa2: Tarefa = Tarefa(10, "Cache 2", "Minha tarefa exemplo", null, 0)
        val tarefa3: Tarefa = Tarefa(10, "Cache 3", "Minha tarefa exemplo", null, 0)
        val tarefa4: Tarefa = Tarefa(10, "Cache 4", "Minha tarefa exemplo", null, 0)
        val tarefa5: Tarefa = Tarefa(10, "Cache 5", "Minha tarefa exemplo", null, 0)
        val tarefa6: Tarefa = Tarefa(10, "Cache 6", "Minha tarefa exemplo", null, 0)
        val tarefa7: Tarefa = Tarefa(10, "Cache 7", "Minha tarefa exemplo", null, 0)
        val tarefa8: Tarefa = Tarefa(10, "Cache 8", "Minha tarefa exemplo", null, 0)

        return listOf(tarefa1, tarefa2, tarefa3, tarefa4, tarefa5, tarefa6, tarefa7, tarefa8)
    }
}