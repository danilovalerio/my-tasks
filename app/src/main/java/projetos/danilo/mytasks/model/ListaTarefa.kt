package projetos.danilo.mytasks.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListaTarefa (
    @SerializedName("listaTarefa")
    val listaTarefa: List<Tarefa>
)