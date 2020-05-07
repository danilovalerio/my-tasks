package projetos.danilo.mytasks.viewmodel.states.tarefas

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasEvent {
    object NovaTarefa: TarefasEvent()
    data class ExibeMensagemCurta(val msg: String): TarefasEvent()
    data class ClickTarefa(val tarefa: Tarefa): TarefasEvent()
}