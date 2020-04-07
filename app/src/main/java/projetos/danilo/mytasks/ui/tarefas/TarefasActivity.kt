package projetos.danilo.mytasks.ui.tarefas

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.toolbarPrincipal
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.adapter.TarefasAdapter
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.provider.providerTarefasUseCase
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_COMENTARIO
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_DESCRICAO
import projetos.danilo.mytasks.ui.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_TITULO
import projetos.danilo.mytasks.ui.base.BaseActivity
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.factory.TarefasViewModelFactory
import projetos.danilo.mytasks.viewmodel.states.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.TarefasInteractor
import projetos.danilo.mytasks.viewmodel.states.TarefasState

class TarefasActivity : BaseActivity(),  SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TarefasAdapter
    lateinit var useCase: TarefasUseCase
    lateinit var viewModel: TarefasViewModel
    lateinit var viewModelFactory: TarefasViewModelFactory
    lateinit var linearLayoutTarefa: LinearLayout


//    private val viewModel by lazy {
//        providerTarefasViewModel(
//            this
//        )
//    }

    private var ultimoTermoProcurado: String = ""
    private var buscaView: SearchView? = null

    val ACTIVITY_ADICIONAR_NOTA_REQUEST = 1
    val CHAR_DEFAULT = "-"
    val ZERO_DEFAULT:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)

        recyclerView = findViewById(R.id.recyclerTarefas)
//        linearLayoutTarefa = findViewById(R.id.ll_adicionartarefa) //layout com opção de add tarefa
//        linearLayoutTarefa.setOnClickListener {
//            viewModel.interpretar(TarefasInteractor.ClickNovaTarefa)
//        }

        inicializarViewModel()

        configurarToolbar(toolbarPrincipal, R.string.titulo_minhas_tarefas)

        //todo: Comentario para remoção
      /*  viewModel.initDatabase(this)

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
                                tarefa.comentario ?: " - ",
                                tarefa.concluida.toString()
                            )

                            this@TarefasActivity.startActivity(intent)
                        }
                }
            }
        })*/

        //todo: Capturar clique do botão
        floatingActionButton.setOnClickListener {
            viewModel.interpretar(TarefasInteractor.ClickNovaTarefa)
//            val intent = Intent(this, AdicionarTarefasActivity::class.java)
//            startActivityForResult(intent, ACTIVITY_ADICIONAR_NOTA_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY_ADICIONAR_NOTA_REQUEST){
            if (resultCode == Activity.RESULT_OK){
//                val resultado = data?.getStringExtra(EXTRA_TITULO) ?: "-"
                val tarefaNova = Tarefa(ZERO_DEFAULT,
                    data?.getStringExtra(EXTRA_TITULO) ?: "-",
                    data?.getStringExtra(EXTRA_DESCRICAO) ?: "-",
                    data?.getStringExtra(EXTRA_COMENTARIO) ?: "-" ,
                    null)

                viewModel.adicionarTarefa(tarefaNova)
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

    fun inicializarViewModel(){
        useCase = providerTarefasUseCase(this)
        viewModelFactory = TarefasViewModelFactory(useCase)
        viewModel = ViewModelProviders.of(
            this,
        viewModelFactory).get(TarefasViewModel::class.java)
//        useCase.repository

        inicializarObservers()
    }

    fun inicializarObservers(){
        viewModel.viewState.observe(this, Observer { viewstate ->
            viewstate?.let {
                when(it){
                    is TarefasState.ListaTarefas -> preencheLista(it.listaTarefa)
                }
            }
        })

        viewModel.viewEvent.observe(this, Observer { viewstate ->
            viewstate?.let {
                when(it){
                    is TarefasEvent.ListaRecolhida -> recolheLista()
                    is TarefasEvent.NovaTarefa -> navegaAddTarefa()
                    is TarefasEvent.TarefaSelecionado -> tarefaSelecionada(it.tarefa)
                    is TarefasEvent.ExibeTelaExclusao -> excluirTarefa(it.tarefa, it.position)
                    is TarefasEvent.TarefaExcluida -> excluir(it.position)
                }
            }
        })
        viewModel.inicializar()
    }

    private fun preencheLista(lista: MutableList<Tarefa>){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TarefasAdapter(lista, viewModel)
    }

    private fun recolheLista(){
        this.finish()
    }

    private fun navegaAddTarefa(){
//        val data = Intent()
        val intent = Intent(this, AdicionarTarefasActivity::class.java)
            startActivityForResult(intent, ACTIVITY_ADICIONAR_NOTA_REQUEST)
    }

    private fun tarefaSelecionada(tarefa: Tarefa){
        val data = Intent()
        data.putExtra("TAREFA_SELECIONADA", Tarefa(tarefa.id,tarefa.titulo, tarefa.descricao,
        tarefa.comentario, tarefa.concluida))
        this.finish()
    }

    private fun excluirTarefa(tarefa: Tarefa, position: Int){
        AlertDialog.Builder(this)
            .setTitle("Excluir Tarefa")
            .setMessage("Deseja confirmar a remoção da tarefa ${tarefa.titulo}?")
            .setPositiveButton(
                "sim"
            ){_,_ ->
                viewModel.interpretar(
                    TarefasInteractor.ClickConfirmarExcluirTarefa(
                        tarefa, position
                    )
                )
            }
            .setNegativeButton("não", null)
            .show()
    }

    private fun excluir(position: Int){
        recyclerView.adapter?.apply {
            notifyItemRemoved(position)
            notifyItemChanged(position, itemCount)
        }
        toolbarPrincipal.requestFocus()
    }



    companion object {
        const val EXTRA_SEARCH_TERM = "ultimaBusca"
    }

}
