package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasInteractor {
    data class ClickExcluirTarefa(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    data class ClickConfirmarExcluir(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    object ClickRecolherTarefas : TarefasInteractor()
    object ClickNovaTarefa : TarefasInteractor()
    data class ClickItem(val tarefa: Tarefa) : TarefasInteractor()
}