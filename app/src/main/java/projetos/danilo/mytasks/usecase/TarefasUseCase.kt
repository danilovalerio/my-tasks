package projetos.danilo.mytasks.usecase

import android.content.Context
import projetos.danilo.mynotesmvvm.data.repository.sqlite.SQLiteRepository
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.repository.TarefasRepository
import projetos.danilo.mytasks.util.verificaTexto

class TarefasUseCase(private val repository: TarefasRepository) {
    lateinit var database: SQLiteRepository

    fun initDatabase(ctx: Context){
        database = SQLiteRepository(ctx)
    }

    fun obterListaDeTarefas() : List<Tarefa> {
        return database.getAllTarefas()
    }

    fun adicionarTarefa(tarefa: Tarefa) {
        val tarefaValidada = Tarefa(0,
            verificaTexto(tarefa.titulo),
            verificaTexto(tarefa.descricao),
            tarefa.comentario?.let { verificaTexto(it) },
            null)

        database.save(tarefaValidada)
    }

    fun buscarTarefasPorTitulo(termo: String) : List<Tarefa>{
        return database.search(termo)
    }

    fun deleteTarefa(id: String){
        database.excluirTarefa(id)
    }

    fun alterarConclusaoTarefa(id: String, concluida: String) {
        database.alterarConclusaoTarefa(id, concluida)
    }

    /** Adicionar COUROTINES */
    suspend fun consultarLista(): MutableList<Tarefa> {
        val listaTarefas = repository.consultarTarefasSQLite()
        if(listaTarefas.isNotEmpty()){
            return listaTarefas
        }
        listaTarefas.addAll(repository.consultarTarefasSQLite())
        return listaTarefas
    }

    suspend fun salvarContaSQLite(): Boolean {
        val listaTarefa = consultarLista()
        var tarefa: Tarefa? = null

        listaTarefa.forEach{
            tarefa = it
        }

        if(tarefa != null){
            tarefa ?: listaTarefa.add(
                Tarefa(
                    0.toLong(),
                    "textoTitulo",
                    "textoDescricao",
                    null,
                    null
                )
            )
            repository.salvarTarefaSQLite(listaTarefa)
            return true
        }
        return false
    }

}