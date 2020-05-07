package projetos.danilo.mytasks.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_adicionar_tarefas.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.AdicionarTarefaViewModel
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor


class AdicionarTarefaFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: AdicionarTarefaViewModel
    private lateinit var viewModelTarefas: TarefasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_adicionar_tarefas, container, false)
//        viewModelTarefas = ViewModelProviders.of(this).get(TarefasViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelTarefas = activity?.run {
            ViewModelProviders.of(this)
                .get(TarefasViewModel::class.java)
        } ?: throw Exception("Activity inválida")

        /** Cria a ViewModel usando um ViewModelProvider */
        viewModel = ViewModelProviders.of(this).get(AdicionarTarefaViewModel::class.java)

        inicializarObservers()
        configurarListeners()
    }

    private fun configurarListeners() {
        btn_cancelar.setOnClickListener { this.dismiss() }

        et_tituloTarefa.addTextChangedListener {
            viewModel.setTitulo(it.toString())
        }

        et_descricaoTarefa.addTextChangedListener {
            viewModel.setDescricao(it.toString())
        }

        et_comentarioTarefa.addTextChangedListener {
            viewModel.setComentario(it.toString())
        }

        btn_adicionar.setOnClickListener { adicionarTarefa() }
    }

    private fun inicializarObservers() {
        viewModel.tarefaAtualizada.observe(this, Observer {
            it?.let {
                configuraTitulo(it.titulo)
//                tv_titulo_fragment.text = it.titulo.toString()
            }
        })
    }

    private fun configuraTitulo(titulo: String) {
        btn_adicionar.isEnabled = !titulo.isNullOrBlank()
    }

    private fun configuraDescricao(descricao: String) {
        if (!descricao.isNullOrBlank()) {
            et_descricaoTarefa.setText(descricao)
        }
    }

    private fun configuraComentario(comentario: String) {
        if (!comentario.isNullOrBlank()) {
            et_comentarioTarefa.setText(comentario)
        }
    }

    private fun adicionarTarefa() {
        val tarefa = viewModel.tarefaAtualizada.value as Tarefa

        viewModelTarefas.adicionarTarefa(tarefa)
        viewModelTarefas.interpretar(TarefasInteractor.ExibeMensagemToastCurta(resources.getString(R.string.texto_tarefa_adicionada_com_sucesso, tarefa.titulo)))
        this.dismiss()
    }
}