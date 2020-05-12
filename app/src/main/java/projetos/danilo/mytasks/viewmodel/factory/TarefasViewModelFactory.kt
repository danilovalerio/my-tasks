package projetos.danilo.mytasks.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.mytasks.data.Repository
import projetos.danilo.mytasks.viewmodel.TarefasViewModel

class TarefasViewModelFactory(private val repository: Repository):
ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TarefasViewModel(repository) as T
    }
}