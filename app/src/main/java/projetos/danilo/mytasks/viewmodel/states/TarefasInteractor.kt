package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasInteractor {
    data class ClickExcluirTarefa(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    data class ClickConfirmarExcluirTarefa(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    data class ClickItem(val tarefa: Tarefa) : TarefasInteractor()
    object ClickRecolherTarefas : TarefasInteractor()
    object ClickNovaTarefa : TarefasInteractor()

}