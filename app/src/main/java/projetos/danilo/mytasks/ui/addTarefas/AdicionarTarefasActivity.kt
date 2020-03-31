package projetos.danilo.mytasks.ui.addTarefas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_adicionar_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.*
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.ui.base.BaseActivity


class AdicionarTarefasActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_tarefas)

        configurarToolbar(toolbarPrincipal, R.string.titulo_adicao, true)

        btn_adicionar.setOnClickListener {
            //todo:Adicionar direto pela viewModel ao invés de mandar um result para TarefasActivity
            val returnIntent = Intent()
            returnIntent.putExtra(EXTRA_TITULO, et_tituloTarefa.text.toString())
            returnIntent.putExtra(EXTRA_DESCRICAO, et_descricaoTarefa.text.toString())
            returnIntent.putExtra(EXTRA_COMENTARIO, et_comentarioTarefa.text.toString())
            setResult(Activity.RESULT_OK, returnIntent)

            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }

    }

    companion object {
        const val EXTRA_TITULO = "TITULO"
        const val EXTRA_DESCRICAO = "DESCRICAO"
        const val EXTRA_COMENTARIO = "COMENTARIO"
    }


}