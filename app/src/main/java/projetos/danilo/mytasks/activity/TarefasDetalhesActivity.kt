package projetos.danilo.mytasks.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_tarefas_details.*
import kotlinx.android.synthetic.main.include_toolbar.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.activity.base.BaseActivity
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.repository.RepositoryImpl
import projetos.danilo.mytasks.repository.TarefasCacheServiceImpl
import projetos.danilo.mytasks.repository.db.TarefaDatabase
import projetos.danilo.mytasks.util.toastLong
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor

class TarefasDetalhesActivity : BaseActivity() {

    private val tarefa by lazy { intent.getParcelableExtra<Tarefa>(TAREFA) }

    private val tareafasdatabase by lazy { TarefaDatabase.invoke(applicationContext) }
    private val cacheService by lazy { TarefasCacheServiceImpl() }
    private val repository by lazy {
        RepositoryImpl(
            cacheService,
            tareafasdatabase
        )
    }
    private val viewModelTarefas by viewModels<TarefasViewModel> { TarefasViewModel.Factory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_details)

        configurarToolbar(toolbarPrincipal, R.string.titulo_detalhe_tarefas, true)

        if(tarefa != null){
            tv_notaDetalheTitulo.text = this.tarefa.titulo
            tv_notaDetalheDescricao.text = this.tarefa.descricao
            tv_notaComentario.text = this.tarefa.comentario
            checkBoxConcluida.isChecked = this.tarefa.concluida == 1
            Log.i("DADOS", "TAREFA ${tarefa.titulo} CONCLUIDA ${tarefa.concluida}")
        }

        configuraListeners()
    }

    private fun configuraListeners() {
        /**valores que vem da intent*/
        imgbtn_excluir.setOnClickListener {
//            viewModelTarefas.excluirTarefa(tarefa)
            AlertDialog.Builder(this)
                .setTitle("Excluir Tarefa")
                .setMessage("Deseja confirmar a remoção da tarefa ${tarefa.titulo}?")
                .setPositiveButton(
                    "sim"
                ){_,_ ->
//                    viewModelTarefas.excluirTarefa(tarefa)
                    viewModelTarefas.interpretar(TarefasInteractor.ClickExcluirTarefa(tarefa))
                    toastLong("Tarefa ${tarefa.titulo} excluída com sucesso!")
                    viewModelTarefas.interpretar(TarefasInteractor.ExibeMensagemToastCurta(
                        "Tarefa ${tarefa.titulo} excluída com sucesso!"
                    ))
                    finish()
//                    viewModel.interpretar(
//                        TarefasInteractor.ClickConfirmarExcluirTarefa(
//                            tarefa, position
//                        )
//                    )
                }
                .setNegativeButton("não", null)
                .show()

        }

        checkBoxConcluida.setOnClickListener {
//            toastShort("Clicou id:"+tarefa.id)
            viewModelTarefas.alteraConclusaoDaTarefa(tarefa)
        }
    }

    /** conteúdo estático */
    companion object{
        private const val TAREFA = "TAREFA"

        fun criarIntent(ctx: Context, tarefa: Tarefa): Intent {
            /**Intent(activity de origem, activity de destino*/
           return Intent(ctx, TarefasDetalhesActivity::class.java).apply {
               putExtra(TAREFA, tarefa)
            }
        }
    }
}