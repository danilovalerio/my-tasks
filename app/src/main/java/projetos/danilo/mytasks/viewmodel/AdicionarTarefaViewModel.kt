package projetos.danilo.mytasks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdicionarTarefaViewModel: ViewModel() {

    val titulo: MutableLiveData<String> = MutableLiveData()
    val descricao: MutableLiveData<String> = MutableLiveData()
    val comentario: MutableLiveData<String> = MutableLiveData()
    
    fun setTitulo(titulo: String){
        this.titulo.postValue(titulo)
    }
    
    fun setDescricao(descricao: String){
        this.descricao.postValue(descricao)
    }
    
    fun setComentario(comentario: String){
        this.comentario.postValue(comentario)
    }
}