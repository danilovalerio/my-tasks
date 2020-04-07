package projetos.danilo.mytasks.provider

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import projetos.danilo.mynotesmvvm.data.repository.sqlite.GerenciadorSQLiteRepository
import projetos.danilo.mytasks.persistencia.GerenciadorTarefaRepository
import projetos.danilo.mytasks.repository.TarefasRepository
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel


fun providerTarefasViewModel(activity: AppCompatActivity): TarefasViewModel {
    return ViewModelProviders.of(
        activity
    ).get(TarefasViewModel::class.java)
}


fun providersTarefasRepository(armazenamento: GerenciadorTarefaRepository): TarefasRepository {
    return TarefasRepository(armazenamento)
}

fun providerTarefasUseCase(context: Context): TarefasUseCase {
    val database = GerenciadorSQLiteRepository(context)

    return TarefasUseCase(
        providersTarefasRepository(database)
    )
}