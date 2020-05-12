package projetos.danilo.mytasks.repository

import projetos.danilo.mytasks.model.Tarefa

data class AllTarefasResponse(
    val todas: List<TarefaResponse>
) {
    fun toListOfTarefas(): List<Tarefa> {
        return todas.map { it.toTarefa() }
    }
}

data class TarefaResponse(
    var id: Int,
    val titulo: String,
    val descricao: String,
    val comentario: String? = "-",
    val concluida: Int? = 0//0 false 1 true
) {
    fun toTarefa() = Tarefa(id,titulo, descricao, comentario, concluida)
}