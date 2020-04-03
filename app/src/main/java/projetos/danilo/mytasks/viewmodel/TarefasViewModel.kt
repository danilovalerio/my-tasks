package projetos.danilo.mytasks.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.model.Tarefa

class TarefasViewModel : ViewModel() {

    val tarefasUseCase = TarefasUseCase()
    val notasLiveData: MutableLiveData<List<Tarefa>> = MutableLiveData()

    fun initDatabase(ctx: Context){
        tarefasUseCase.initDatabase(ctx)
    }

    //Podemos usar couroutines para solicitar informação assíncrona (Async)
    fun getListaTarefas(){
        setListaTarefas(tarefasUseCase.obterListaDeTarefas())
    }

    fun setListaTarefas(listaTarefas:List<Tarefa>){
        notasLiveData.value = listaTarefas
    }

    fun adicionarTarefa(tarefa: Tarefa){
        tarefasUseCase.adicionarTarefa(tarefa)
        getListaTarefas()
    }

    fun buscaPorTiulo(termo: String){
        if (termo.isNotEmpty()){
            notasLiveData.value = tarefasUseCase.buscarTarefasPorTitulo(termo)
        } else {
            getListaTarefas()
        }
    }

    fun deletarTarefa(id: String){
        tarefasUseCase.deleteTarefa(id)
    }

    fun alterarConclusao(id: String, concluida: String){
        tarefasUseCase.alterarConclusaoTarefa(id, concluida)
    }
}