package projetos.danilo.mytasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.TarefasViewModel


class TarefasAdapter(
    val tarefas: MutableList<Tarefa>,
    val viewModel: TarefasViewModel
//    val onItemClickListener: (tarefa: Tarefa) -> Unit
) /**lâmbda ao inves da interface*/
    : RecyclerView.Adapter<TarefasViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarefasViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefasViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tarefas.count()
    }

    override fun onBindViewHolder(holder: TarefasViewHolder, position: Int) {
        holder.bindView(tarefas[position], viewModel, position)
    }

    fun setTarefas(tarefasAtualizadas: MutableList<Tarefa>){
        tarefas.clear()
        tarefas.addAll(tarefasAtualizadas)
        notifyDataSetChanged()
    }
}