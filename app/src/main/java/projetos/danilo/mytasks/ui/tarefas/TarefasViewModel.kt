package projetos.danilo.mytasks.ui.tarefas

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import projetos.danilo.mytasks.domain.TarefasUseCase
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

    fun adicionarNota(nota: Tarefa){
        tarefasUseCase.adicionarTarefa(nota)
        getListaTarefas()
    }

    fun buscaPorTiulo(termo: String){
        if (termo.isNotEmpty()){
            notasLiveData.value = tarefasUseCase.buscarTarefasPorTitulo(termo)
        } else {
            getListaTarefas()
        }
    }

    fun deletarNota(id: String){
        tarefasUseCase.deleteTarefa(id)
    }
}