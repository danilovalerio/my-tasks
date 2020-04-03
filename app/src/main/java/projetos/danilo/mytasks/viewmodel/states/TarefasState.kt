package projetos.danilo.mytasks.viewmodel.states

import projetos.danilo.mytasks.model.Tarefa

/** classe selada evita criação de heranças */
sealed class TarefasState {
    data class ListaTarefas(val listaTarefa: MutableList<Tarefa>) : TarefasState()
}