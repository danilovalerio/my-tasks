package projetos.danilo.mytasks.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasState
import kotlinx.coroutines.launch
import projetos.danilo.mynotesmvvm.data.repository.sqlite.GerenciadorSQLiteRepository
import projetos.danilo.mytasks.model.ListaTarefa
import projetos.danilo.mytasks.data.Repository
import projetos.danilo.mytasks.provider.providerTarefasUseCase
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor

class TarefasViewModel(private val repository: Repository) : ViewModel() {
    private val state: MutableLiveData<TarefasState> = MutableLiveData()
    private val event: MutableLiveData<TarefasEvent> = MutableLiveData()

    val viewState: LiveData<TarefasState> = state
    val viewEvent: LiveData<TarefasEvent> = event

    val tarefas = MutableLiveData<List<Tarefa>>()

    private lateinit var gerenciadorSQLiteRepository: GerenciadorSQLiteRepository

    fun inicializar(context: Context){
//        useCase = providerTarefasUseCase(context)

        viewModelScope.launch {
            event.postValue(TarefasEvent.SuccessGetAll(repository.getListTarefa() as MutableList<Tarefa>))
//            state.postValue(TarefasState.ListaTarefas(useCase.consultarLista()))
//            event.postValue(TarefasEvent.SuccessGetAll(useCase.consultarLista()))
            tarefas.value = repository.getListTarefa()
        }
    }

    fun interpretar(interactor: TarefasInteractor){
        Log.i("InteractorViewModel: ",interactor.toString())
        when (interactor) {
//            is TarefasInteractor.ClickNovaTarefa -> abreTelaAdicionarTarefa()
            is TarefasInteractor.AdicionarTarefa -> adicionarTarefa(interactor.tarefa)
        }
    }

//    private fun abreTelaAdicionarTarefa(){
//        event.value = TarefasEvent.NovaTarefa //ativa evento que leva para tela de adicionar tarefa
//    }

    private fun adicionarTarefa(tarefa: Tarefa){
        GlobalScope.launch {
//            useCase.adicionarTarefa(tarefa)
        }
    }

    private fun excluir(tarefa: Tarefa, position: Int){
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
//    private fun recolheLista(){
//        event.value = TarefasEvent.ListaRecolhida
//    }
//
//    //todo: WIP
//    private fun salvarTarefa(tarefa: Tarefa){
//        event.value = TarefasEvent.AdicionarTarefa(tarefa)
//        GlobalScope.launch {
////            useCase.adicionarTarefa(tarefa)
////            state.postValue(TarefasState.ListaTarefas(useCase.consultarLista()))
//        }
//
//    }

    fun buscaPorTiulo(termo: String){
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