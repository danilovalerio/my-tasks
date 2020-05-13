package projetos.danilo.mytasks.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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


        btn_adicionar.setBackgroundColor(resources.getColor(R.color.transparent))

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

        btn_adicionar.setOnClickListener {
            if(!et_tituloTarefa.text.isNullOrBlank()){
                adicionarTarefa()
            } else {
                Toast.makeText(context, "Título obrigatório", Toast.LENGTH_SHORT).show()
            }
        }

        et_tituloTarefa.onFocusChangeListener = View.OnFocusChangeListener(){ view: View, b: Boolean ->
            Log.i("DADOS", "TITULO PERDEU O FOCO hasFocus "+ b)
            if(!b){
                validaEditText(et_tituloTarefa, til_tituloTarefa, et_tituloTarefa.hint)
            }
        }
    }

    private fun inicializarObservers() {
        viewModel.tarefaAtualizada.observe(viewLifecycleOwner, Observer {
            it?.let {
                configuraTitulo(it.titulo)
            }
        })
    }

    private fun configuraTitulo(titulo: String) {
        //todo: Exibir mensagem de obrigatoriedade do título
        if (!titulo.isNullOrBlank()){
            btn_adicionar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }


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
        viewModelTarefas.interpretar(TarefasInteractor.ExibeMensagemToastCurta(
            resources.getString(R.string.texto_tarefa_adicionada_com_sucesso, tarefa.titulo))
        )
        this.dismiss()
    }

    //todo: Extrair para utils validaçoes
    /** valida se o EditText está vazio e com tamanho mínimo quando obrigatório*/
    private fun validaEditText(editText: TextInputEditText, inputLayout: TextInputLayout,
                               textoInformativo: CharSequence?){
        if(editText.text.isNullOrBlank()){
            inputLayout.error = "${textoInformativo.toString()} obrigatório"
            inputLayout.setErrorTextAppearance(R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        } else {
            inputLayout.error = ""
        }
    }
}