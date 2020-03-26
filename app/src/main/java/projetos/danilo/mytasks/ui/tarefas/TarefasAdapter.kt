package projetos.danilo.mytasks.ui.tarefas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa


class TarefasAdapter(
    val tarefas: List<Tarefa>,
    val onItemClickListener: (tarefa: Tarefa) -> Unit
) /**lâmbda ao inves da interface*/
    : RecyclerView.Adapter<TarefasViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarefasViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefasViewHolder(
            itemView,
            onItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return tarefas.count()
    }

    override fun onBindViewHolder(holder: TarefasViewHolder, position: Int) {
        holder.bindView(tarefas[position])
    }

}