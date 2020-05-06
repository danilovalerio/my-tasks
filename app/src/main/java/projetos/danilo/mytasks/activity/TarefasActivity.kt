package projetos.danilo.mytasks.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.toolbarPrincipal
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.adapter.TarefasAdapter
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.activity.addTarefas.AdicionarTarefasActivity
import projetos.danilo.mytasks.activity.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_COMENTARIO
import projetos.danilo.mytasks.activity.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_DESCRICAO
import projetos.danilo.mytasks.activity.addTarefas.AdicionarTarefasActivity.Companion.EXTRA_TITULO
import projetos.danilo.mytasks.activity.base.BaseActivity
import projetos.danilo.mytasks.activity.detalhes.TarefasDetalhesActivity
import projetos.danilo.mytasks.data.RepositoryImpl
import projetos.danilo.mytasks.data.TarefasCacheServiceImpl
import projetos.danilo.mytasks.util.toastShort
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.factory.TarefasViewModelFactory
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor

class TarefasActivity : BaseActivity(),  SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var recyclerViewTarefas: RecyclerView
    private lateinit var adapterTarefas: TarefasAdapter

    private var ultimoTermoProcurado: String = ""
    private var buscaView: SearchView? = null

    val ACTIVITY_ADICIONAR_NOTA_REQUEST = 1
    val ZERO_DEFAULT:Long = 0


    //utilizar injecao de dependencia
    private val cacheService by lazy { TarefasCacheServiceImpl() }
    private val repository by lazy { RepositoryImpl(cacheService) }
    /** TarefasViewModel.Factory(repository): Utiliza a factory disponível na viewmodel*/
    private val viewModel by viewModels<TarefasViewModel> { TarefasViewModel.Factory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)

        configurarBind()

        iniciarViewModel()
        inicializarObservers()

    }

    private fun configurarBind(){
        recyclerViewTarefas = findViewById(R.id.recyclerTarefas)
        configurarToolbar(toolbarPrincipal, R.string.titulo_minhas_tarefas)

        floatingActionButton.setOnClickListener { abrirBottomSheetAdicionarTarefa() }
    }

    private fun iniciarViewModel(){
        viewModel.inicializar(this)
    }

    fun inicializarObservers(){
        viewModel.tarefas.observe(this, Observer {
            Log.i("DADOS", it.toString())
//            successCall(it as MutableList<Tarefa>)
        })

        viewModel.viewEvent.observe(this, Observer { viewstate ->
            viewstate?.let {
                when(it){
                    is TarefasEvent.SuccessGetAll -> successCall(it.tarefas)
                }
            }
        })

        viewModel.viewState.observe(this, Observer { viewstate ->
            viewstate?.let {
                when(it){
//                    is TarefasState.ListaTarefas -> {
//                        Log.i("IteratorListaTarefas","preenche a lista"+it.listaTarefa.toString())
//                        preencheLista(it.listaTarefa)
//                    }
                }
            }
        })
    }

    private fun abrirBottomSheetAdicionarTarefa(){
        abriBottomSheet(AdicionarTarefaFragment())
    }

    private fun abriBottomSheet(bottomSheet: BottomSheetDialogFragment){
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun vaiParaAdicionarTarefa(){
        val intent = Intent(this, AdicionarTarefasActivity::class.java)
        startActivity(intent)
    }

    private fun navegarParaTalhes(tarefa: Tarefa){
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

    private fun tarefaSelecionada(tarefa: Tarefa){
        val data = Intent()
        data.putExtra("TAREFA_SELECIONADA", Tarefa(tarefa.id,tarefa.titulo, tarefa.descricao,
        tarefa.comentario, tarefa.concluida))
        setResult(Activity.RESULT_OK, data)
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
        recyclerViewTarefas.adapter?.apply {
            notifyItemRemoved(position)
            notifyItemChanged(position, itemCount)
        }
        toolbarPrincipal.requestFocus()
    }

    override fun finish() {
        val semAnimacao = 0
        super.finish()
        overridePendingTransition(semAnimacao, R.anim.slide_up_tarefas)
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

//                viewModel.adicionarTarefa(tarefaNova)
            }
        }

        Log.i("TAREFAS_ACTIVITY", "onActivityResult")

//        inicializarObservers()
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
            R.id.action_ocultar -> {
//                aoClicarItemMenu()
            }
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

    private fun successCall(tarefas: MutableList<Tarefa>){
        configuraAdapter(tarefas)
    }

    private fun configuraAdapter(tarefas: MutableList<Tarefa>){
        adapterTarefas = TarefasAdapter(tarefas, viewModel)

        recyclerViewTarefas.layoutManager = LinearLayoutManager(this)
        recyclerViewTarefas.setHasFixedSize(true)
        recyclerViewTarefas.adapter = adapterTarefas

        adapterTarefas.notifyDataSetChanged()

        if(tarefas.size == 0) {
            Log.i("MENSAGEM","NENHUMA TAREFA ENCONTRADA")
        }
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "ultimaBusca"
    }

}
