package projetos.danilo.mytasks.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.mytasks.model.Tarefa

class AdicionarTarefaViewModel : ViewModel() {
    //todo: implementar um interactor para deixar as funções privadas

    val tarefaAtualizada: MutableLiveData<Tarefa> = MutableLiveData()
    val botaoAdicionarAtivado: MutableLiveData<Boolean> = MutableLiveData()

    private var tarefaAtual: Tarefa = Tarefa(0, "", "", "", 0)
//    private var tarefaAtual: Tarefa

    fun setTitulo(titulo: String) {
        botaoAdicionarAtivado.postValue(tamanhoMinimo(titulo))
        tarefaAtual.titulo = titulo
        tarefaAtualizada.postValue(tarefaAtual)
    }

    fun setDescricao(descricao: String) {
        if (descricao.length > 3) {
            tarefaAtual.descricao = descricao
            tarefaAtualizada.postValue(tarefaAtual)
        }
    }

    fun setComentario(comentario: String) {
        if (comentario.length > 3) {
            tarefaAtual.comentario = comentario
            tarefaAtualizada.postValue(tarefaAtual)
        }
    }

    fun tamanhoMinimo(texto: String) : Boolean {
        Log.i("DADOS", "tamanho minimo: "+texto.length)
        if (!texto.isNullOrBlank() && texto.length > 2){
            return true
        }
        return false
    }

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AdicionarTarefaViewModel() as T
        }
    }
}