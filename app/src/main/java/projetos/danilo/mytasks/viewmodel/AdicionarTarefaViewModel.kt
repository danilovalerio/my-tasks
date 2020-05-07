package projetos.danilo.mytasks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.mytasks.model.Tarefa

class AdicionarTarefaViewModel: ViewModel() {

    val tarefaAtualizada: MutableLiveData<Tarefa> = MutableLiveData()
    val titulo: MutableLiveData<String> = MutableLiveData()
    val descricao: MutableLiveData<String> = MutableLiveData()
    val comentario: MutableLiveData<String> = MutableLiveData()

    private var tarefa: Tarefa = Tarefa(0, "", "", "", 0)
    
    fun setTitulo(titulo: String){
        if(titulo.length > 3){
            this.tarefa.titulo = titulo
            tarefaAtualizada.value = tarefa
        }
    }
    
    fun setDescricao(descricao: String){
        this.descricao.postValue(descricao)
    }
    
    fun setComentario(comentario: String){
        this.comentario.postValue(comentario)
    }

    fun adicionarTarefa(){

    }

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AdicionarTarefaViewModel() as T
        }
    }
}