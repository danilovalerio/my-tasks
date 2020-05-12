package projetos.danilo.mytasks.model

import com.google.gson.annotations.SerializedName

data class ListaTarefa (
    @SerializedName("listaTarefa")
    val listaTarefa: List<Tarefa>
)