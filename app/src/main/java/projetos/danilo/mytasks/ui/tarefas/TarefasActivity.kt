package projetos.danilo.mytasks.ui.tarefas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.toolbarPrincipal
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_TITULO
import projetos.danilo.mytasks.ui.base.BaseActivity
import projetos.danilo.mytasks.ui.detalhes.TarefasDetalhesActivity

class TarefasActivity : BaseActivity() {
    private val viewModel by lazy {
        providerTarefasViewModel(
            this
        )
    }

    val ACTIVITY_ADICIONAR_NOTA_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)

        configurarToolbar(toolbarPrincipal, R.string.titulo_minhas_tarefas)

        viewModel.initDatabase(this)

        viewModel.notasLiveData.observe(this, Observer {
            it?.let { tarefas ->
                with(recyclerTarefas) {
                    layoutManager = LinearLayoutManager(
                        this@TarefasActivity, //chama o contexto de NotasActicity
                        RecyclerView.VERTICAL,
                        false
                    )
                    setHasFixedSize(true)

                    adapter =
                        TarefasAdapter(tarefas) { tarefa ->
                            val intent = TarefasDetalhesActivity.getStartIntent(
                                this@TarefasActivity,
                                tarefa.titulo,
                                tarefa.descricao,
                                tarefa.comentario ?: " - "
                            )

                            this@TarefasActivity.startActivity(intent)
                        }
                }
            }
        })

        //todo: Capturar clique do botão
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AdicionarTarefasActivity::class.java)
            startActivityForResult(intent, ACTIVITY_ADICIONAR_NOTA_REQUEST)
        }

        viewModel.getListaTarefas()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY_ADICIONAR_NOTA_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                val resultado = data?.getStringExtra(EXTRA_TITULO) ?: "-"
                val tarefaNova = Tarefa(0, resultado, "nota criada", null , 1)

                viewModel.adicionarNota(tarefaNova)
            }
        }
    }
}
