package projetos.danilo.mynotesmvvm.data.repository.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.data.GerenciadorTarefaRepository
import projetos.danilo.mytasks.repository.*

class GerenciadorSQLiteRepository :
    GerenciadorTarefaRepository {
    private var helper: TarefaSqlHelper

    constructor(ctx: Context){
        helper = TarefaSqlHelper(ctx)
    }

    var notasMutableList: MutableList<Tarefa> = mutableListOf()
    lateinit var notaLiveData: MutableLiveData<Tarefa>
    lateinit var notasLiveData: MutableLiveData<List<Tarefa>>

    private fun insert(tarefa: Tarefa){
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_TITULO, tarefa.titulo)
            put(COLUMN_DESCRICAO, tarefa.descricao)
            put(COLUMN_COMENTARIO, tarefa.comentario)
            put(COLUMN_CONCLUIDA, tarefa.concluida)
        }

        val id = db.insert(TABLE_TAREFA, null, cv)
        if(id != -1L){
            tarefa.id = id
        }
        db.close()
    }

    private fun update(tarefa: Tarefa){
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_ID, tarefa.id)
            put(COLUMN_TITULO, tarefa.titulo)
            put(COLUMN_DESCRICAO, tarefa.descricao)
            put(COLUMN_COMENTARIO, tarefa.comentario)
            put(COLUMN_CONCLUIDA, tarefa.concluida)
        }

        db.insertWithOnConflict(
            TABLE_TAREFA,
            null,
            cv,
            SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    override fun <T> get(clazz: Class<T>?): T {
        TODO("Not yet implemented")
    }

    override fun <T> put(chave: String?, valor: T) {
        TODO("Not yet implemented")
    }

    override fun <T> setLista(chave: String?, lista: Array<T>?) {
        TODO("Not yet implemented")
    }

    override fun <T> getLista(clazz: Class<Array<T>?>?): List<Tarefa>? {
        TODO("Not yet implemented")
    }

    override fun contemValorParaChave(chave: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deletar(chave: String?) {
        TODO("Not yet implemented")
    }

    override fun limpar() {
        TODO("Not yet implemented")
    }

    override fun save(tarefa: Tarefa) {
        if (tarefa.id == 0L){
            insert(tarefa)
        } else {
            update(tarefa)
        }
    }

    override fun remove(vararg tarefas: Tarefa) {
        val db = helper.writableDatabase
        for (nota in tarefas){
            db.delete(
                TABLE_TAREFA,
                "$COLUMN_ID = ?",
                arrayOf(nota.id.toString())
            )
        }
        db.close()
    }

    override fun tarefaById(id: Long): LiveData<Tarefa> {
        val sql = "SELECT * FROM $TABLE_TAREFA WHERE $COLUMN_ID = ?"
        val db = helper.readableDatabase

        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val hotel = if(cursor.moveToNext()) tarefaFromCursor(cursor) else null

        notaLiveData.value = hotel

        return notaLiveData
    }

    override fun search(term: String):  MutableList<Tarefa> {
        if(notasMutableList.size > 0) notasMutableList.clear()
        var sql = "SELECT * FROM $TABLE_TAREFA "
        var args: Array<String>? = null
        if(term.isNotEmpty()){
            sql += "WHERE $COLUMN_TITULO LIKE ? "
            args = arrayOf("%$term%")
        }
        sql += "ORDER BY $COLUMN_TITULO"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val notas = ArrayList<Tarefa>()

        while (cursor.moveToNext()){
            val nota = tarefaFromCursor(cursor)
            notasMutableList.add(nota)
        }

        cursor.close()
        db.close()

        return notasMutableList
    }

    override fun getAllTarefas(): MutableList<Tarefa> {

        var listaTarefa: MutableList<Tarefa> = mutableListOf()
//        if(notasMutableList.size > 0) notasMutableList.clear()
//        if(listaTarefa. > 0) notasMutableList.clear()

        val sql = "SELECT * FROM $TABLE_TAREFA"
        val db = helper.readableDatabase

        val cursor = db.rawQuery(sql, null)

        while (cursor.moveToNext()){
            val tarefa = tarefaFromCursor(cursor)
            listaTarefa.add(tarefa)
        }

        cursor.close()
        db.close()

        return listaTarefa
    }

    fun excluirTarefa(id: String){
        val db = helper.writableDatabase

        val args = id
        db.delete("$TABLE_TAREFA", "$COLUMN_ID = ?", arrayOf(args))
        db.close()
    }

    fun alterarConclusaoTarefa(id:String, concluida: String){
        var alteraConcluida = "0"

        if(concluida == "0"){
            alteraConcluida = "1"
        } else {
            alteraConcluida = "0"
        }
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_CONCLUIDA, alteraConcluida)
        }

        db.insertWithOnConflict(
            TABLE_TAREFA,
            null,
            cv,
            SQLiteDatabase.CONFLICT_REPLACE)

        db.close()
    }

    private fun tarefaFromCursor(cursor: Cursor): Tarefa {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
        val descricao = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO))
        val comentario = cursor.getString(cursor.getColumnIndex(COLUMN_COMENTARIO))
        val concluida = cursor.getInt(cursor.getColumnIndex(COLUMN_CONCLUIDA))

        return Tarefa(titulo, descricao, comentario, concluida)
    }


}