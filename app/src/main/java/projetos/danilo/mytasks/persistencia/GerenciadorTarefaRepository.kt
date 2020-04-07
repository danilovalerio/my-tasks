package projetos.danilo.mytasks.persistencia

import androidx.lifecycle.LiveData
import projetos.danilo.mynotesmvvm.data.repository.sqlite.GerenciadorSQLiteRepository
import projetos.danilo.mytasks.model.ListaTarefa
import projetos.danilo.mytasks.model.Tarefa

/** Similar ao GerenciadorArmazenamento em persistencia */
interface GerenciadorTarefaRepository {
//    var database: GerenciadorSQLiteRepository

    operator fun <T> get(clazz: Class<T>?): T

    fun <T> put(chave: String?, valor: T)

    fun <T> setLista(chave: String?, lista: Array<T>?)

    fun <T> getLista(
        clazz: Class<Array<T>?>?
    ): List<Tarefa>?

    fun contemValorParaChave(chave: String?): Boolean

    fun deletar(chave: String?)

    fun limpar()

    fun save(tarefa: Tarefa)
    fun remove(vararg tarefas: Tarefa)
    fun tarefaById(id: Long) : LiveData<Tarefa>
    fun search(term: String): MutableList<Tarefa>
    fun getAllTarefas(): MutableList<Tarefa>

}