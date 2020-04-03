package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasEvent {
    data class TarefaSelecionado(val tarefa: Tarefa) : TarefasEvent()
    data class ExibeTelaExclusao(val tarefa: Tarefa, val position: Int) : TarefasEvent()
    data class TarefaExcluida(val tarefa: Tarefa, val position: Int) : TarefasEvent()
    object ListaRecolhida: TarefasEvent()
    object NovaTarefa: TarefasEvent()
}