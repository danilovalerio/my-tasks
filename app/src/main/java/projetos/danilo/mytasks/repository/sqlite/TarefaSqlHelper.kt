package projetos.danilo.mynotesmvvm.data.repository.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import projetos.danilo.mytasks.*

class TarefaSqlHelper(ctx: Context) :
    SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(
            "CREATE TABLE $TABLE_TAREFA("+
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "$COLUMN_TITULO TEXT NOT NULL,"+
                    "$COLUMN_DESCRICAO TEXT NOT NULL,"+
                    "$COLUMN_COMENTARIO TEXT"+
                    "$COLUMN_DESCRICAO INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Atualização de versão
    }
}