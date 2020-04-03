package projetos.danilo.mytasks.ui.tarefas

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import projetos.danilo.mytasks.viewmodel.TarefasViewModel


fun providerTarefasViewModel(activity: AppCompatActivity) : TarefasViewModel {
    return ViewModelProviders.of(
        activity
    ).get(TarefasViewModel::class.java)
}