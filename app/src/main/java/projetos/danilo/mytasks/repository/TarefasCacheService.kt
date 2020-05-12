package projetos.danilo.mytasks.repository

import projetos.danilo.mytasks.model.Tarefa

interface TarefaCacheService {
    fun getTarefas(): List<Tarefa>
}

class TarefasCacheServiceImpl :
    TarefaCacheService {
    override fun getTarefas(): List<Tarefa> {
        val tarefa1: Tarefa = Tarefa(
            0,
            "Cache 1",
            "Minha tarefa exemplo",
            "comentário de exemplo bla bal balbalbalblalbal labll bal la lbalb lalbal bal bla ",
            0
        )
        val tarefa2: Tarefa = Tarefa(1,"Cache 2", "Minha tarefa exemplo", null, 0)
        val tarefa3: Tarefa = Tarefa(
            2,
            "Cache 3",
            "Minha tarefa exemplo",
            "comentário de exemplo bla bal balbalbalblalbal labll bal la lbalb lalbal bal bla ",
            0
        )
        val tarefa4: Tarefa = Tarefa(3,"Cache 4", "Minha tarefa exemplo", null, 0)
        val tarefa5: Tarefa = Tarefa(4,
            "Cache 5",
            "Minha tarefa exemplo",
            "comentário de exemplo bla bal balbalbalblalbal labll bal la lbalb lalbal bal bla ",
            0
        )
        val tarefa6: Tarefa = Tarefa(5,"Cache 6", "Minha tarefa exemplo", null, 0)
        val tarefa7: Tarefa = Tarefa(
            6,
            "Cache 7",
            "Minha tarefa exemplo",
            "comentário de exemplo bla bal balbalbalblalbal labll bal la lbalb lalbal bal bla ",
            0
        )
        val tarefa8: Tarefa = Tarefa(7,"Cache 8", "Minha tarefa exemplo", null, 0)

        return listOf(tarefa1, tarefa2)
    }
}