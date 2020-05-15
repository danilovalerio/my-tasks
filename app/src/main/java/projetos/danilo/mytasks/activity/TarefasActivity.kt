package projetos.danilo.mytasks.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tarefas.*
import kotlinx.android.synthetic.main.include_toolbar.toolbarPrincipal
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.activity.base.BaseActivity
import projetos.danilo.mytasks.adapter.TarefasAdapter
import projetos.danilo.mytasks.model.Tarefa
import projetos.danilo.mytasks.repository.RepositoryImpl
import projetos.danilo.mytasks.repository.TarefasCacheServiceImpl
import projetos.danilo.mytasks.repository.db.TarefaDatabase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasEvent
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasInteractor
import projetos.danilo.mytasks.viewmodel.states.tarefas.TarefasState

class TarefasActivity : BaseActivity() {
    //utilizar injecao de dependencia
    private val tareafasdatabase by lazy { TarefaDatabase.invoke(applicationContext) }
    private val cacheService by lazy { TarefasCacheServiceImpl() }
    private val repository by lazy {
        RepositoryImpl(
            cacheService,
            tareafasdatabase
        )
    }

    /** TarefasViewModel.Factory(repository): Utiliza a factory disponível na viewmodel*/
    private val viewModel by viewModels<TarefasViewModel> { TarefasViewModel.Factory(repository) }

    private lateinit var recyclerViewTarefas: RecyclerView
    private lateinit var adapterTarefas: TarefasAdapter
    private lateinit var snackbar: Snackbar

    private var ultimoTermoProcurado: String = ""
    private var buscaView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)

        configurarBind()

        iniciarViewModel()
        inicializarObservers()
    }

    override fun onResume() {
        super.onResume()
        Log.i("DADOS", "VOLTAMOS PARA ACTIVITY TAREFAS")
        viewModel.inicializar()
    }

    private fun configurarBind() {
        recyclerViewTarefas = findViewById(R.id.recyclerTarefas)
        configurarToolbar(toolbarPrincipal, R.string.titulo_minhas_tarefas)

        floatingActionButton.setOnClickListener {
//            abrirBottomSheetAdicionarTarefa() //abrindo direto
            viewModel.interpretar(TarefasInteractor.ClickNovaTarefa)
        }
    }

    private fun iniciarViewModel() {
        viewModel.inicializar()
    }

    fun inicializarObservers() {
        viewModel.viewState.observe(this, Observer { viewstate ->
            viewstate?.let {
                when (it) {
                    is TarefasState.ListaTarefas -> successCall(it.listaTarefa)
                }
            }
        })

        viewModel.viewEvent.observe(this, Observer { viewstate ->
            viewstate?.let {
                when (it) {
                    is TarefasEvent.NovaTarefa -> abrirBottomSheetAdicionarTarefa()
                    is TarefasEvent.ExibeMensagemCurta -> exibirMensagemCurta(it.msg)
                    is TarefasEvent.ClickTarefa -> navegarParaTalhes(it.tarefa)
                }
            }
        })
    }

    private fun abrirBottomSheetAdicionarTarefa() {
        abriBottomSheet(AdicionarTarefaFragment())
    }

    private fun abriBottomSheet(bottomSheet: BottomSheetDialogFragment) {
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun navegarParaTalhes(tarefa: Tarefa) {
        Log.i("DADOS", " Tarefa para Intent: " + tarefa.toString())
        val intent = TarefasDetalhesActivity.criarIntent(this, tarefa)
        startActivity(intent)
    }

    private fun successCall(tarefas: MutableList<Tarefa>) {
        Log.i("SUCCESS CALL DADOS", tarefas.toString())
        configurarViewFlipper(tarefas)
    }

    private fun configurarViewFlipper(tarefas: MutableList<Tarefa>) {
        if (tarefas.size == 0) {
            //todo: adicionar uma View com a mensagem
            Log.i("DADOS MENSAGEM", "NENHUMA TAREFA ENCONTRADA")
            viewFlipperTarefas.displayedChild = 2 //todo: criar constante com nome de erro
            tvError.text = "Nenhuma tarefa encontrada..."
        } else {
            configuraAdapter(tarefas)
            viewFlipperTarefas.displayedChild = 0 //todo: criar constante com nome de tarefas
        }
    }

    private fun configuraAdapter(tarefas: MutableList<Tarefa>) {
        adapterTarefas = TarefasAdapter(tarefas, viewModel)
        recyclerViewTarefas.layoutManager = LinearLayoutManager(this)
        recyclerViewTarefas.setHasFixedSize(true)
        recyclerViewTarefas.adapter = adapterTarefas
        adapterTarefas.notifyDataSetChanged()
    }

    private fun exibirMensagemCurta(msg: String) {
        configuraSnackBar(msg)
    }

    private fun configuraSnackBar(msg: String){
        snackbar = Snackbar.make(floatingActionButton, msg, Snackbar.LENGTH_LONG)
        snackbar.setAction("FECHAR", View.OnClickListener {
            snackbar.dismiss()
        })

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        snackbar.setTextColor(resources.getColor(R.color.white))
        snackbar.setActionTextColor(resources.getColor(R.color.colorAccent))
        snackbar.show()
    }
}
