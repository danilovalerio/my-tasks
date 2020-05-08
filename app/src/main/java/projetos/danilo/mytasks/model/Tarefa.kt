package projetos.danilo.mytasks.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import projetos.danilo.mytasks.util.CHAR_DEFAULT


@Parcelize
@Entity
data class Tarefa (
    var titulo: String,
    var descricao: String,
    var comentario: String? = "-",
    var concluida: Int? = 0//0 false 1 true
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}