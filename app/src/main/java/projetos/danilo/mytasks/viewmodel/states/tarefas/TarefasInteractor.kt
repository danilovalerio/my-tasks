package projetos.danilo.mytasks.viewmodel.states.tarefas

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasInteractor {
    /** Interação que leva para tela de nova tarefa */
    object ClickNovaTarefa : TarefasInteractor()

    /** Recolher a lista de tarefas */
    data class AdicionarTarefa(val tarefa: Tarefa) : TarefasInteractor()


    object ClickRecolherTarefas : TarefasInteractor()
    data class ClickExcluirTarefa(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    data class ClickConfirmarExcluirTarefa(val tarefa: Tarefa, val position: Int) : TarefasInteractor()
    data class ClickItem(val tarefa: Tarefa) : TarefasInteractor()


}