package projetos.danilo.mytasks.domain

import android.content.Context
import projetos.danilo.mynotesmvvm.data.repository.sqlite.SQLiteRepository
import projetos.danilo.mytasks.model.Tarefa

class TarefasUseCase {
    lateinit var database: SQLiteRepository

    fun initDatabase(ctx: Context){
        database = SQLiteRepository(ctx)
    }

    fun obterListaDeTarefas() : List<Tarefa> {
        return database.getAllTarefas()
    }

    fun adicionarTarefa(tarefa: Tarefa) {
        database.save(tarefa)
    }

    fun buscarTarefaaPorTitulo(termo: String){
        database.search(termo)
    }
}