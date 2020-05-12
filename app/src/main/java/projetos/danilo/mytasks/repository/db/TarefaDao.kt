package projetos.danilo.mytasks.repository.db

import androidx.room.*
import projetos.danilo.mytasks.model.Tarefa

@Dao
interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionarTarefa(tarefa: Tarefa)

    @Query("SELECT * FROM Tarefa")
    suspend fun getAllTarefas() : List<Tarefa>

    @Insert
    suspend fun addMultiplasTarefas(vararg tarefa: Tarefa)

    @Delete
    suspend fun deletarTarefa(tarefa: Tarefa)

    @Query("SELECT * FROM Tarefa ORDER BY titulo, id")
    suspend fun getAllTarefasOrdemAlfabetica() : List<Tarefa>

    @Query("DELETE FROM Tarefa WHERE id = :id")
    suspend fun excluirPorId(id: Int)

    @Query("UPDATE Tarefa SET concluida = :concluida WHERE id = :id")
    suspend fun alterarConclusao(id: Int,  concluida: Int)

}