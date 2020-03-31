package projetos.danilo.mytasks.ui.tarefas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
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
import projetos.danilo.mytasks.util.toastLong

class TarefasActivity : BaseActivity(),  SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private val viewModel by lazy {
        providerTarefasViewModel(
            this
        )
    }

    private var ultimoTermoProcurado: String = ""
    private var buscaView: SearchView? = null

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
                                tarefa.id,
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

//        viewModel.getListaTarefas()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY_ADICIONAR_NOTA_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                val resultado = data?.getStringExtra(EXTRA_TITULO) ?: "-"
                val tarefaNova = Tarefa(0, resultado, "nota criada", null , null)

                viewModel.adicionarNota(tarefaNova)
            }
        }
    }

    /** Configuração das opções na Action Bar */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tarefa, menu)

        val buscaTarefa = menu?.findItem(R.id.action_pesquisa)
        buscaTarefa?.setOnActionExpandListener(this)
        buscaView = buscaTarefa?.actionView as SearchView
        buscaView?.queryHint = getString(R.string.action_hint_pesquisa)
        buscaView?.setOnQueryTextListener(this)

        if(ultimoTermoProcurado.isNotEmpty()){
            Handler().post{
                val query = ultimoTermoProcurado
                buscaTarefa.expandActionView()
                buscaView?.setQuery(query, true)
                buscaView?.clearFocus()
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
//            R.id.action_nova -> {
//                val intent = Intent(this, AdicionarTarefasActivity::class.java)
//                startActivityForResult(intent, ACTIVITY_ADICIONAR_NOTA_REQUEST)
//            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        ultimoTermoProcurado = newText?: ""
        Log.i("TEXTO_PROCURADO", ultimoTermoProcurado)
        if(ultimoTermoProcurado.length > 3){
            viewModel.buscaPorTiulo(ultimoTermoProcurado)
        }

        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        Log.i("EXPANDIR","EXPANDINDO SEARCHVIEW")
        onQueryTextSubmit("")
        return true //para expandir a View
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        ultimoTermoProcurado = ""
        //limpa a busca para voltar ao normal
        Log.i("TEXTO_PROCURADO", "TEXTO LIMPO"+ultimoTermoProcurado)
        viewModel.buscaPorTiulo(ultimoTermoProcurado)
        return true
    }

    override fun onResume() {
        super.onResume()
        /** Mantém nossa lista atualizada */
        viewModel.getListaTarefas()
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "ultimaBusca"
    }

}
