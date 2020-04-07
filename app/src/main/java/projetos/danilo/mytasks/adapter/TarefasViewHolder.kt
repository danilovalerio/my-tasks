package projetos.danilo.mytasks.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tarefa.view.*
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.states.TarefasInteractor


class TarefasViewHolder(
    itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titulo = itemView.tv_tituloTarefa
    val descricao = itemView.tv_descricaoTarefa
    val comentario = itemView.tv_comentarioTarefa
    val concluida = itemView.checkBoxConcluida

    fun bindView(tarefa: Tarefa, viewModel: TarefasViewModel, position: Int){
        titulo.text = tarefa.titulo
        descricao.text = tarefa.descricao
        comentario.text = tarefa.comentario
        if (tarefa.concluida == 1){
            concluida.isChecked = true
        }
        itemView.setOnClickListener {
            viewModel.interpretar(TarefasInteractor.ClickItem(tarefa))
        }
    }
}