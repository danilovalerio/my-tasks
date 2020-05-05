package projetos.danilo.mytasks.viewmodel.states.tarefas

import projetos.danilo.mytasks.model.Tarefa

sealed class TarefasEvent {
    data class SuccessGetAll(val tarefas: MutableList<Tarefa>) : TarefasEvent()


//    object NovaTarefa: TarefasEvent()
//    data class AdicionarTarefa(val tarefa: Tarefa) : TarefasEvent()
//    object ListaRecolhida: TarefasEvent()
//    data class TarefaSelecionado(val tarefa: Tarefa) : TarefasEvent()
//    data class ExibeTelaExclusao(val tarefa: Tarefa, val position: Int) : TarefasEvent()
//    data class TarefaExcluida(val tarefa: Tarefa, val position: Int) : TarefasEvent()
//    data class TarefaDetalhes(val tarefa: Tarefa) : TarefasEvent()
}