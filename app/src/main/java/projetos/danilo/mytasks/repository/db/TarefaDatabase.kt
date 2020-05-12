package projetos.danilo.mytasks.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import projetos.danilo.mytasks.model.Tarefa

@Database(
    entities = [Tarefa::class],
    version = 1
)
abstract class TarefaDatabase : RoomDatabase() {
    abstract fun getTarefaDao() : TarefaDao

    companion object {
        @Volatile
        private var instance: TarefaDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(
                    context
                ).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TarefaDatabase::class.java,
            "Tarefa-Database.db"
        ).build()
    }
}