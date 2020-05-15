package projetos.danilo.mytasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Tarefa (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var titulo: String,
    var descricao: String? = "-",
    var comentario: String? = "-",
    val concluida: Int? = 0//0 false 1 true
) : Parcelable