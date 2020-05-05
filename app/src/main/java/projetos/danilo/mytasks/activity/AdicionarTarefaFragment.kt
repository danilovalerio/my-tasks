package projetos.danilo.mytasks.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_adicionar_tarefas.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.viewmodel.AdicionarTarefaViewModel
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor
import android.text.TextWatcher as TextWatcher1


class AdicionarTarefaFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AdicionarTarefaViewModel
    private lateinit var viewModelTarefas: TarefasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_adicionar_tarefas, container, false)

        inicializarViewModel()
        inicializarObservers()

        return view
    }

    private fun inicializarViewModel() {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(AdicionarTarefaViewModel::class.java)
        }
            ?: throw Exception("Activity inválida")

        viewModelTarefas = ViewModelProviders.of(this).get(TarefasViewModel::class.java)

    }

    private fun iniciarBind() {
        btn_cancelar.setOnClickListener {
            this.dismiss()
        }
//        btn_adicionar.setOnClickListener {  }
    }

    private fun inicializarObservers() {
        viewModel.titulo.observe(this, Observer {
            it?.let {
                configuraTitulo(it)
            }
        })

        viewModel.descricao.observe(this, Observer {
            it?.let {
                configuraDescricao(it)
            }
        })

        viewModel.comentario.observe(this, Observer {
            it?.let {
                configuraComentario(it)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        et_tituloTarefa.addTextChangedListener(object : TextWatcher1 {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                configuraTitulo(s.toString())
            }
        })

        btn_cancelar.setOnClickListener { this.dismiss() }
        btn_adicionar.setOnClickListener {
            val tarefa = Tarefa(
                0,
                et_tituloTarefa.text.toString(),
                et_descricaoTarefa.text.toString(),
                et_comentarioTarefa.text.toString(),
                0
            )

            viewModelTarefas.interpretar(TarefasInteractor.AdicionarTarefa(tarefa))

            Toast.makeText(
                context,
                "Adicionar tarefa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_tarefas)

        configurarToolbar(toolbarPrincipal, R.string.titulo_adicao, true)

        inicializarViewModel()

        btn_adicionar.setOnClickListener {
            //todo:Adicionar direto pela viewModel ao invés de mandar um result para TarefasActivity
//            val returnIntent = Intent()
//            returnIntent.putExtra(EXTRA_TITULO, et_tituloTarefa.text.toString())
//            returnIntent.putExtra(EXTRA_DESCRICAO, et_descricaoTarefa.text.toString())
//            returnIntent.putExtra(EXTRA_COMENTARIO, et_comentarioTarefa.text.toString())
//            setResult(Activity.RESULT_OK, returnIntent)

            val tarefa = Tarefa(0,
                et_tituloTarefa.text.toString(),
                et_descricaoTarefa.text.toString(),
                et_comentarioTarefa.text.toString(),
                0)

            viewModel.interpretar(TarefasInteractor.AdicionarTarefa(tarefa))

            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }

    }*/

    private fun adicionar(tarefa: Tarefa) {
        val data = Intent()
        data.putExtra(
            "TAREFA_SELECIONADA", Tarefa(
                tarefa.id, tarefa.titulo, tarefa.descricao,
                tarefa.comentario, tarefa.concluida
            )
        )
//        setResult(Activity.RESULT_OK, data)
    }


    companion object {
        const val EXTRA_TITULO = "TITULO"
        const val EXTRA_DESCRICAO = "DESCRICAO"
        const val EXTRA_COMENTARIO = "COMENTARIO"
    }


}