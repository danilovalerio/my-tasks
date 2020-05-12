package projetos.danilo.mytasks.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import projetos.danilo.mytasks.util.CHAR_DEFAULT


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