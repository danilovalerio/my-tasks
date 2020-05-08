package projetos.danilo.mytasks.repository

import projetos.danilo.mytasks.model.Tarefa

fun createFakeNotas() {
    var tarefas: MutableList<Tarefa> = mutableListOf()

    tarefas.add(Tarefa( "Tarefa 1", "Comprar item 1", null,0))
    tarefas.add(Tarefa( "Tarefa 2", "Comprar item 2", "Com comentário de teste 2",0))
    tarefas.add(Tarefa( "Tarefa 3", "Comprar item 3", null,0))
    tarefas.add(Tarefa( "Tarefa 4", "Comprar item 4", "Com comentário de teste 4",0))
    tarefas.add(Tarefa( "Tarefa 5", "Comprar item 5", null,0))
    tarefas.add(Tarefa( "Tarefa 6", "Comprar item 6", "Com comentário de teste 6",0))
}