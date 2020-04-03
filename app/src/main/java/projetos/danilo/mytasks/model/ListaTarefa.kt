package projetos.danilo.mytasks.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListaTarefa (
    @SerializedName("listaTarefa")
    val listaTarefa: List<Tarefa>
): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.createTypedArrayList(Tarefa) ?: emptyList()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(listaTarefa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListaTarefa>{
        override fun createFromParcel(source: Parcel): ListaTarefa {
            return ListaTarefa(source)
        }

        override fun newArray(size: Int): Array<ListaTarefa?> {
            return arrayOfNulls(size)
        }

    }
}