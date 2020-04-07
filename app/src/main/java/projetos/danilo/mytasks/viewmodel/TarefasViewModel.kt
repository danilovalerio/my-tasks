package projetos.danilo.mytasks.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.states.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.TarefasState
import kotlinx.coroutines.launch
import projetos.danilo.mytasks.viewmodel.states.TarefasInteractor

class TarefasViewModel(private val useCase: TarefasUseCase) : BaseViewModel() {
    private val state: MutableLiveData<TarefasState> = MutableLiveData()
    private val event: MutableLiveData<TarefasEvent> = MutableLiveData()
    val viewState: LiveData<TarefasState> = state
    val viewEvent: LiveData<TarefasEvent> = event

    //todo: CONTINUAR A APRTIR DAQUI A REFATORAÇÃO

    fun inicializar(){
        launch {
            state.postValue(TarefasState.ListaTarefas(useCase.consultarLista()))
        }
    }

    fun interpretar(interactor: TarefasInteractor){
        when (interactor) {
            is TarefasInteractor.ClickExcluirTarefa -> excluir(
                interactor.tarefa,
                interactor.position
            )

            is TarefasInteractor.ClickConfirmarExcluirTarefa -> confirmaExcluir(
                interactor.tarefa,
                interactor.position
            )
            is TarefasInteractor.ClickItem -> tarefaSelecionada(interactor.tarefa)
            is TarefasInteractor.ClickNovaTarefa -> navegaAddTarefa()
            is TarefasInteractor.ClickRecolherTarefas -> recolheLista()
        }
    }

    private fun excluir(tarefa: Tarefa, position: Int){
        launch {
            useCase.deleteTarefa(position.toString()) //todo: Passar conta para exclusao
        }
    }

    private fun confirmaExcluir(tarefa: Tarefa, position:Int){
        event.value = TarefasEvent.ExibeTelaExclusao(tarefa, position)
    }

    private fun tarefaSelecionada(tarefa: Tarefa){
        event.value = TarefasEvent.TarefaSelecionado(tarefa)
    }

    private fun navegaAddTarefa(){
        launch {
            event.value = TarefasEvent.NovaTarefa
        }
    }

    private fun recolheLista(){
        event.value = TarefasEvent.ListaRecolhida
    }




    //todo: Remover daqui para baixo os cod antigos
    val tarefasUseCase = useCase
    val notasLiveData: MutableLiveData<List<Tarefa>> = MutableLiveData()

//    fun initDatabase(ctx: Context){
//        tarefasUseCase.initDatabase(ctx)
//    }

    //Podemos usar couroutines para solicitar informação assíncrona (Async)
    fun getListaTarefas(){
//        setListaTarefas(tarefasUseCase.obterListaDeTarefas())
    }

    fun setListaTarefas(listaTarefas:List<Tarefa>){
        notasLiveData.value = listaTarefas
    }

    fun adicionarTarefa(tarefa: Tarefa){
        tarefasUseCase.adicionarTarefa(tarefa)
        getListaTarefas()
    }

    fun buscaPorTiulo(termo: String){
//        if (termo.isNotEmpty()){
//            notasLiveData.value = tarefasUseCase.buscarTarefasPorTitulo(termo)
//        } else {
//            getListaTarefas()
//        }
    }

    fun deletarTarefa(id: String){
        tarefasUseCase.deleteTarefa(id)
    }

    fun alterarConclusao(id: String, concluida: String){
        tarefasUseCase.alterarConclusaoTarefa(id, concluida)
    }
}