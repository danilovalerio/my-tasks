package projetos.danilo.mytasks.ui.detalhes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tarefas_details.*
import kotlinx.android.synthetic.main.include_toolbar.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.ui.base.BaseActivity
import projetos.danilo.mytasks.ui.tarefas.providerTarefasViewModel
import projetos.danilo.mytasks.util.toastLong
import projetos.danilo.mytasks.util.toastShort

class TarefasDetalhesActivity : BaseActivity() {

    private val viewModelTarefas by lazy {
        providerTarefasViewModel(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_details)

        configurarToolbar(toolbarPrincipal, R.string.titulo_detalhe_tarefas, true)
        viewModelTarefas.initDatabase(this)

        /**valores que vem da intent*/
        tv_notaDetalheTitulo.text = intent.getStringExtra(EXTRA_TITULO)
        tv_notaDetalheDescricao.text = intent.getStringExtra(EXTRA_DESCRICAO)
        tv_notaComentario.text = intent.getStringExtra(EXTRA_COMENTARIO)

        imgbtn_excluir.setOnClickListener {
            val idParaExclusao = intent.getStringExtra(EXTRA_ID)
            val tituloNotaExcluida = intent.getStringExtra(EXTRA_TITULO)
            viewModelTarefas.deletarTarefa(idParaExclusao)
            toastLong("Tarefa $tituloNotaExcluida excluída com sucesso!")
            finish()
        }

        checkBoxConcluida.setOnClickListener {
            val idParaAtualizar = intent.getStringExtra(EXTRA_ID)
            val conclusao = intent.getStringExtra(EXTRA_CONCLUSAO)
            toastShort("Clicou")
            viewModelTarefas.alterarConclusao(idParaAtualizar, conclusao)
        }
    }

    /** objeto Nota para essa activity*/
    companion object{
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_TITULO = "EXTRA_TITULO"
        private const val EXTRA_DESCRICAO = "EXTRA_DESCRICAO"
        private const val EXTRA_COMENTARIO = "EXTRA_COMENTARIO"
        private const val EXTRA_CONCLUSAO = "EXTRA_CONCLUSAO"

        fun getStartIntent(ctx: Context, id: Long, titulo: String, descricao: String, comentario: String, conclusao: String): Intent {
            /**Intent(activity de origem, activity de destino*/
            val intent = Intent(ctx, TarefasDetalhesActivity::class.java)
            intent.putExtra(EXTRA_ID, id.toString())
            intent.putExtra(EXTRA_TITULO, titulo)
            intent.putExtra(EXTRA_DESCRICAO, descricao)
            intent.putExtra(EXTRA_COMENTARIO, comentario)
            intent.putExtra(EXTRA_CONCLUSAO, conclusao)

            return intent
        }
    }
}