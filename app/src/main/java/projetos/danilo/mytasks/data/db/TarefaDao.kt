package projetos.danilo.mytasks.data.db

import androidx.room.*
import projetos.danilo.mytasks.model.Tarefa

@Dao
interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionarTarefa(tarefa: Tarefa)

    @Query("SELECT * FROM tarefa")
    suspend fun getAllTarefas() : List<Tarefa>

    @Insert
    suspend fun addMultiplasTarefas(vararg tarefa: Tarefa)

    @Delete
    suspend fun deletarTarefa(tarefa: Tarefa)

}