package projetos.danilo.mytasks.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import projetos.danilo.mytasks.data.Repository
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasState

class TarefasViewModel(private val repository: Repository) : ViewModel() {
    private val state: MutableLiveData<TarefasState> = MutableLiveData()
    private val event: MutableLiveData<TarefasEvent> = MutableLiveData()

    val viewState: LiveData<TarefasState> = state
    val viewEvent: LiveData<TarefasEvent> = event

    var tarefas = MutableLiveData<MutableList<Tarefa>>()

    var tarefasMut: MutableList<Tarefa> = mutableListOf()

    fun inicializar() {
        viewModelScope.launch {
            state.postValue(TarefasState.ListaTarefas(repository.getListTarefa() as MutableList<Tarefa>))
            tarefasMut.addAll(repository.getListTarefa() as MutableList<Tarefa>) //todo: teste, remover posteriormente
        }
    }

    /** Interpretar as ações do usuário como cliques e também são mapeados por sealed class */
    fun interpretar(interactor: TarefasInteractor) {
        Log.i("InteractorViewModel: ", interactor.toString())
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

    fun adicionarTarefa(tarefa: Tarefa) {
        tarefasMut.add(tarefa)
        tarefas.postValue(tarefasMut)
        state.postValue(TarefasState.ListaTarefas(tarefasMut))
    }

    private fun exibirMensagem(msg: String) {
        viewModelScope.launch {
            event.value = TarefasEvent.ExibeMensagemCurta(msg)
        }
    }

    private fun tarefaClicada(tarefa: Tarefa) {
        event.value = TarefasEvent.ClickTarefa(tarefa)
    }

    private fun excluir(tarefa: Tarefa, position: Int) {
        GlobalScope.launch {
//            useCase.deleteTarefa(position.toString()) //todo: Passar conta para exclusao
        }
    }

    //    private fun confirmaExcluir(tarefa: Tarefa, position:Int){
//        event.value = TarefasEvent.ExibeTelaExclusao(tarefa, position)
//    }
//
//    private fun tarefaSelecionada(tarefa: Tarefa){
//        event.value = TarefasEvent.TarefaSelecionado(tarefa)
//    }
//
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