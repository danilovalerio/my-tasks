package projetos.danilo.mytasks.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel

class TarefasViewModelFactory(private val useCase: TarefasUseCase):
ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TarefasViewModel(useCase) as T
    }
}