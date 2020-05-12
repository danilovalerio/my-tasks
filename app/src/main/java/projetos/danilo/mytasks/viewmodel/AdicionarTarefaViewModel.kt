package projetos.danilo.mytasks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.mytasks.model.Tarefa

class AdicionarTarefaViewModel: ViewModel() {

    val tarefaAtualizada: MutableLiveData<Tarefa> = MutableLiveData()
//    var titulo: MutableLiveData<String> = MutableLiveData()
//    var descricao: MutableLiveData<String> = MutableLiveData()
//    var comentario: MutableLiveData<String> = MutableLiveData()

    private lateinit var tituloAtualizado: String
    private lateinit var descricaoAtualiado: String
    private lateinit var comentarioAtualizado: String

    private var tarefaAtual: Tarefa = Tarefa(0,"", "", "", 0)
//    private var tarefaAtual: Tarefa
    
    fun setTitulo(titulo: String){
        if(titulo.length > 3){
            tarefaAtual.titulo = titulo
            tarefaAtualizada.postValue(tarefaAtual)
        }
    }
    
    fun setDescricao(descricao: String){
        if(descricao.length > 3){
            tarefaAtual.descricao = descricao
            tarefaAtualizada.postValue(tarefaAtual)
        }
    }
    
    fun setComentario(comentario: String){
        if(comentario.length > 3){
            tarefaAtual.comentario = comentario
            tarefaAtualizada.postValue(tarefaAtual)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AdicionarTarefaViewModel() as T
        }
    }
}