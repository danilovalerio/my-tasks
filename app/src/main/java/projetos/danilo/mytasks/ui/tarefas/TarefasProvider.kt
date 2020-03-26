package projetos.danilo.mytasks.ui.tarefas

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders


fun providerTarefasViewModel(activity: AppCompatActivity) : TarefasViewModel {
    return ViewModelProviders.of(
        activity
    ).get(TarefasViewModel::class.java)
}