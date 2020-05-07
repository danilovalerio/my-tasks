package projetos.danilo.mytasks.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import projetos.danilo.mytasks.util.CHAR_DEFAULT

data class Tarefa (
    @SerializedName("id")
    var id: Long,
    @SerializedName("titulo")
    var titulo: String,
    @SerializedName("descricao")
    var descricao: String,
    @SerializedName("comentario")
    var comentario: String? = "-",
    @SerializedName("concluida")
    var concluida: Int? = 0//0 false 1 true
) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readLong(),
        parcel.readString()?: CHAR_DEFAULT,
        parcel.readString()?: CHAR_DEFAULT,
        parcel.readString() ?: CHAR_DEFAULT,
        parcel.readInt() ?: 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(titulo)
        parcel.writeString(descricao)
        parcel.writeString(comentario)
        parcel.writeValue(concluida)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tarefa> {
        override fun createFromParcel(parcel: Parcel): Tarefa {
            return Tarefa(parcel)
        }

        override fun newArray(size: Int): Array<Tarefa?> {
            return arrayOfNulls(size)
        }
    }
}