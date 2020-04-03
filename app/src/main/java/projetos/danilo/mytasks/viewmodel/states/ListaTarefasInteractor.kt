package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

sealed class ListaTarefasInteractor {
    data class ClickExcluirTarefa(val tarefa: Tarefa, val position: Int) : ListaTarefasInteractor()
    data class ClickConfirmarExcluir(val tarefa: Tarefa, val position: Int) : ListaTarefasInteractor()
    object ClickRecolherTarefas : ListaTarefasInteractor()
    object ClickNovaTarefa : ListaTarefasInteractor()
    data class ClickItem(val tarefa: Tarefa) : ListaTarefasInteractor()
}