package projetos.danilo.mytasks.activity.addTarefas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_adicionar_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.provider.providerTarefasUseCase
import projetos.danilo.mytasks.activity.base.BaseActivity
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.factory.TarefasViewModelFactory
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor


class AdicionarTarefasActivity : BaseActivity() {

    lateinit var useCase: TarefasUseCase
    lateinit var viewModel: TarefasViewModel
    lateinit var viewModelFactory: TarefasViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
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

    }

    private fun adicionar(tarefa: Tarefa){
        val data = Intent()
        data.putExtra("TAREFA_SELECIONADA", Tarefa(tarefa.id,tarefa.titulo, tarefa.descricao,
            tarefa.comentario, tarefa.concluida))
        setResult(Activity.RESULT_OK, data)
    }

    fun inicializarViewModel() {
//        useCase = providerTarefasUseCase()
//        viewModelFactory = TarefasViewModelFactory(repository)
//        viewModel = ViewModelProviders.of(
//            this,
//            viewModelFactory
//        ).get(TarefasViewModel::class.java)
    }

    companion object {
        const val EXTRA_TITULO = "TITULO"
        const val EXTRA_DESCRICAO = "DESCRICAO"
        const val EXTRA_COMENTARIO = "COMENTARIO"
    }


}