package projetos.danilo.mytasks.ui.tarefas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tarefa.view.*
import projetos.danilo.mytasks.model.Tarefa


class TarefasViewHolder(
    itemView: View,
    private val onItemClickListener:((tarefa: Tarefa) -> Unit)
) : RecyclerView.ViewHolder(itemView) {
    val titulo = itemView.tv_tituloTarefa
    val descricao = itemView.tv_descricaoTarefa
    val comentario = itemView.tv_comentarioTarefa
    val concluida = itemView.switch_concluida

    fun bindView(tarefa: Tarefa){
        titulo.text = tarefa.titulo
        descricao.text = tarefa.descricao
        comentario.text = tarefa.comentario
        if (tarefa.concluida == 1){
            concluida.isChecked = true
        }
        

        itemView.setOnClickListener {
            onItemClickListener.invoke(tarefa)
        }
    }
}