package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

sealed class ListaTarefasEvent {
    data class TarefaSelecionado(val tarefa: Tarefa) : ListaTarefasEvent()
    data class ExibeTelaExclusao(val tarefa: Tarefa, val position: Int) : ListaTarefasEvent()
    data class TarefaExcluida(val tarefa: Tarefa, val position: Int) : ListaTarefasEvent()
    object ListaRecolhida: ListaTarefasEvent()
    object NovaTarefa: ListaTarefasEvent()
}