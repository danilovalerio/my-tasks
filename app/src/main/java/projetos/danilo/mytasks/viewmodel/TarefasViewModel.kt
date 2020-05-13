package projetos.danilo.mytasks.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import projetos.danilo.mytasks.repository.Repository
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasState

class TarefasViewModel(private val repository: Repository) : ViewModel() {
    private val state: MutableLiveData<TarefasState> = MutableLiveData()
    private val event: MutableLiveData<TarefasEvent> = MutableLiveData()

    val viewState: LiveData<TarefasState> = state
    val viewEvent: LiveData<TarefasEvent> = event

    var tarefasMut: MutableList<Tarefa> = mutableListOf()

    fun inicializar() {
        viewModelScope.launch {
            state.postValue(TarefasState.ListaTarefas(repository.getListTarefa() as MutableList<Tarefa>))
        }
    }

    /** Interpretar as ações do usuário como cliques e mapeados por sealed class */
    fun interpretar(interactor: TarefasInteractor) {
        when (interactor) {
            is TarefasInteractor.ClickNovaTarefa -> navegaTelaAdicionarTarefa()
            is TarefasInteractor.ExibeMensagemToastCurta -> exibirMensagem(interactor.msg)

            is TarefasInteractor.ClickItem -> tarefaClicada(interactor.tarefa)
//            is TarefasInteractor.AdicionarTarefa -> adicionarTarefa(interactor.tarefa)
        }
    }

    private fun navegaTelaAdicionarTarefa() {
        viewModelScope.launch {
            event.value = TarefasEvent.NovaTarefa
        }
    }

    //todo: deixar private e criar interactor
    fun adicionarTarefa(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.adicionarTarefa(tarefa)
            state.postValue(TarefasState.ListaTarefas(repository.getListTarefa() as MutableList<Tarefa>))
        }

    }

    private fun exibirMensagem(msg: String) {
        viewModelScope.launch {
            event.value = TarefasEvent.ExibeMensagemCurta(msg)
        }
    }

    private fun tarefaClicada(tarefa: Tarefa) {
        event.value = TarefasEvent.ClickTarefa(tarefa)
    }

    //todo: deixar private e criar interactor
    fun excluirTarefa(tarefa: Tarefa) {
        tarefasMut.clear()
        viewModelScope.launch {
            try {
                repository.deletarTarefa(tarefa)
                Log.i("DADOS", "DADOS POS EXCLUSAO: " + repository.getListTarefa())
            } catch (e: Exception) {
                Log.i("DADOS", "ERRO NA EXCLUSAO: " + e.toString())
            }
        }
    }

    fun alteraConclusaoDaTarefa(tarefa: Tarefa) {
        var concluidaAlteracao = tarefa.concluida
        if(concluidaAlteracao == 0){
            concluidaAlteracao = 1
        } else {
            concluidaAlteracao = 0
        }
        viewModelScope.launch {
            try {
                repository.alterarConclusao(tarefa.id, concluidaAlteracao)
                inicializar()
                Log.i("DADOS", "SUCESSO AO ALTERAR CONCLUSAO: " + repository.getListTarefa())
            } catch (e: Exception) {
                Log.i("DADOS", "ERRO AO ALTERAR CONCLUSAO: " + e.toString())
            }
        }

    }

    fun exibeOcultaTarefasConcluidas(exibirConcluidas: Int){
        viewModelScope.launch {
            try {
//                repository.exibirOcultarConcluidas(exibirConcluidas)
                state.postValue(TarefasState.ListaTarefas(repository.exibirOcultarConcluidas(exibirConcluidas) as MutableList<Tarefa>))
                Log.i("DADOS", "LISTA RETORNADA: "+repository.exibirOcultarConcluidas(exibirConcluidas).toString())
            } catch (e: Exception) {
                Log.i("DADOS", "ERRO AO ALTERAR CONCLUSAO: " + e.toString())
            }
        }

    }

    fun buscaPorTiulo(termo: String) {
//        if (termo.isNotEmpty()){
//            notasLiveData.value = tarefasUseCase.buscarTarefasPorTitulo(termo)
//        } else {
//            getListaTarefas()
//        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TarefasViewModel(repository) as T
        }
    }
}