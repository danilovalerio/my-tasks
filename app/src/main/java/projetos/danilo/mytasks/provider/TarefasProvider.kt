package projetos.danilo.mytasks.provider

import android.content.Context
import projetos.danilo.mynotesmvvm.data.repository.sqlite.GerenciadorSQLiteRepository
import projetos.danilo.mytasks.data.GerenciadorTarefaRepository
import projetos.danilo.mytasks.repository.TarefasRepository
import projetos.danilo.mytasks.usecase.TarefasUseCase

fun providerTarefaRepository(): TarefasRepository{
    val gerenciadorTarefaRepository: GerenciadorTarefaRepository? = null
    return TarefasRepository(gerenciadorTarefaRepository)
}

fun providerTarefaRepository(armazenamento: GerenciadorTarefaRepository): TarefasRepository {
    return TarefasRepository(armazenamento)
}

fun providerTarefasUseCase(): TarefasUseCase {
    return TarefasUseCase(providerTarefaRepository())
}

fun providerTarefasUseCase(context: Context): TarefasUseCase {
    val database = GerenciadorSQLiteRepository(context)

    return TarefasUseCase(
        providerTarefaRepository(database)
    )
}