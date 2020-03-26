package projetos.danilo.mytasks.model

data class Tarefa (
    var id: Long,
    val titulo: String,
    val descricao: String,
    val comentario: String,
    val concluida: Int //0 false 1 true
)