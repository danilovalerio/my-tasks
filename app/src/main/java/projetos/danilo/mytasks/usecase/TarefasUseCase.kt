package projetos.danilo.mytasks.usecase

import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.repository.TarefasRepository
import projetos.danilo.mytasks.util.verificaTexto

class TarefasUseCase(private val repository: TarefasRepository) {

    /** Adicionar COUROTINES */
    /*Consulta a lista do banco*/
    suspend fun consultarLista(): MutableList<Tarefa> {
        val listaTarefas = repository.consultarTarefas()
        if(listaTarefas.isNotEmpty()){
            return listaTarefas
        }
        listaTarefas.addAll(repository.consultarTarefas())
        return listaTarefas
        return mutableListOf()
    }

    //todo: ADICIONAR TAREFA
    suspend fun adicionarTarefa(tarefa: Tarefa): MutableList<Tarefa> {

        val tarefaValidada = Tarefa(0,
            verificaTexto(tarefa.titulo),
            verificaTexto(tarefa.descricao),
            tarefa.comentario?.let { verificaTexto(it) },
            null)

        repository.salvarTarefa(tarefaValidada)

        val listaAtualizada = repository.consultarTarefas()

        return listaAtualizada
    }



    fun obterListaDeTarefas()  {
//        return database.getAllTarefas()
    }

    fun adicionarTarefaOld(tarefa: Tarefa) {
        val tarefaValidada = Tarefa(0,
            verificaTexto(tarefa.titulo),
            verificaTexto(tarefa.descricao),
            tarefa.comentario?.let { verificaTexto(it) },
            null)

//        database.save(tarefaValidada)
    }

    fun buscarTarefasPorTitulo(termo: String) {
//        return database.search(termo)
    }

    fun deleteTarefa(id: String){
//        database.excluirTarefa(id)
    }

    fun alterarConclusaoTarefa(id: String, concluida: String) {
//        database.alterarConclusaoTarefa(id, concluida)
    }





    suspend fun salvarTarefaSQLite(): Boolean {
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