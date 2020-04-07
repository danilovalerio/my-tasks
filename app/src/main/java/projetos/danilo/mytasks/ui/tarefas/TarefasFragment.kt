package projetos.danilo.mytasks.ui.tarefas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import projetos.danilo.mytasks.R
import projetos.danilo.mytasks.adapter.TarefasAdapter
import projetos.danilo.mytasks.usecase.TarefasUseCase
import projetos.danilo.mytasks.viewmodel.TarefasViewModel
import projetos.danilo.mytasks.viewmodel.factory.TarefasViewModelFactory
import projetos.danilo.mytasks.viewmodel.states.TarefasInteractor

class TarefasFragment : Fragment() {

//    lateinit var recyclerView: RecyclerView
//    lateinit var toolbar: Toolbar
//    lateinit var adapter: TarefasAdapter
//    lateinit var useCase: TarefasUseCase
//    lateinit var viewModel: TarefasViewModel
//    lateinit var viewModelFactory: TarefasViewModelFactory
//    lateinit var linearOutraConta: LinearLayout
//    lateinit var viewRoot: ConstraintLayout
////    lateinit var biRecolherLista: ButtonIcon
////    lateinit var multiplasContasListener: TarefasListener
//    lateinit var viewAccountContent: LinearLayout
//    lateinit var viewOverlay: View
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_multiplas_contas, container, false)
//
//        iniciarViews(view)
//        linearOutraConta.setOnClickListener {
//            viewModel.interpretar(TarefasInteractor.ClickNovaConta)
//        }
//        inicializarViewModel()
//
//        return view
//    }
//
//    private fun iniciarViews(view: View) {
//        recyclerView = view.findViewById(R.id.recycler_multiplas_contas)
//        linearOutraConta = view.findViewById(R.id.ll_outra_conta)
//        toolbar = view.findViewById(R.id.toolbar)
//        viewRoot = view.findViewById(R.id.multiplas_contas_view_root)
//        biRecolherLista = view.findViewById(R.id.bi_multiplas_contas_botao_recolher_lista)
//        biRecolherLista.setOnClickListener {
//            recolheListaDeContas()
//        }
//        biRecolherLista.contentDescription =
//            view.resources.getString(R.string.multiplas_contas_acessibilidade_recolher_lista_operadores)
//        viewAccountContent = view.findViewById(R.id.view_account_list_content)
//        viewOverlay = view.findViewById(R.id.view_account_list_overlay)
//        viewOverlay.setOnClickListener { recolheListaDeContas() }
//    }
//
//    private fun inicializarViewModel() {
//        val mContext: Context? = context
//        if (mContext != null) {
//            useCase = provideTarefasUseCase(
//                mContext,
//                TarefasRepository.STORAGE_MULTIPLAS_CONTAS
//            )
//            viewModelFactory = TarefasViewModelFactory(useCase)
//            viewModel = ViewModelProviders.of(
//                this,
//                viewModelFactory
//            ).get(TarefasViewModel::class.java)
//            inicializarObservers()
//        }
//    }
//
//    private fun inicializarObservers() {
//        viewModel.viewState.observe(this, Observer { viewstate ->
//            viewstate?.let {
//                when (it) {
//                    is TarefasState.ListaContas -> preencheLista(it.listaConta)
//                }
//            }
//        })
//
//        viewModel.viewEvent.observe(this, Observer { viewstate ->
//            viewstate?.let {
//                when (it) {
//                    is TarefasEvent.ListaRecolhida -> recolheListaDeContas()
//                    is TarefasEvent.NovaConta -> navegaLogin()
//                    is TarefasEvent.ContaSelecionada -> contaSelecionada(it.conta)
//                    is TarefasEvent.ExibeTelaExclusao -> excluirConta(it.conta, it.position)
//                    is TarefasEvent.ContaExcluida -> excluir(it.position)
//                }
//            }
//        })
//        viewModel.inicializar()
//    }
//
//    fun setClickListener(multiplasContasListener: TarefasListener) {
//        this.multiplasContasListener = multiplasContasListener
//    }
//
//    private fun preencheLista(lista: MutableList<Conta>) {
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = TarefasAdapter(lista, viewModel)
//    }
//
//    private fun navegaLogin() {
//        exibeListaDeContas()
//        multiplasContasListener.clickNavegarLogin(true)
//    }
//
//    private fun contaSelecionada(conta: Conta) {
//        recolheListaDeContas()
//        multiplasContasListener.clickItemConta(OperadorVO(conta.nome, conta.operador))
//    }
//
//    private fun excluirConta(conta: Conta, position: Int) {
//        val mContext: Context? = context
//        if (mContext != null) {
//            AlertDialog.Builder(mContext)
//                .setTitle(this.getString(R.string.multiplas_contas_remocao_operador_titulo))
//                .setMessage(this.getString(R.string.multiplas_contas_remocao_operador, conta.operador))
//                .setPositiveButton(
//                    R.string.sim
//                ) { _, _ ->
//                    viewModel.interpretar(
//                        TarefasInteractor.ClickConfirmaExcluirConta(
//                            conta,
//                            position
//                        )
//                    )
//                }
//                .setNegativeButton(R.string.nao, null)
//                .show()
//        }
//    }
//
//    private fun excluir(position: Int) {
//        recyclerView.adapter?.apply {
//            notifyItemRemoved(position)
//            notifyItemChanged(position, itemCount)
//        }
//        toolbar.requestFocus()
//    }
//
//    fun exibeListaDeContas() {
//        viewRoot.visibility = View.VISIBLE
//
//        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top_with_fade)
//        animation.duration = ANIMATE_DURATION.toLong()
//        animation.fillAfter = true
//        viewAccountContent.startAnimation(animation)
//
//        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
//        animation.duration = ANIMATE_DURATION.toLong()
//        animation.fillAfter = true
//        viewOverlay.startAnimation(animation)
//        toolbar.findViewById<TextView>(R.id.tv_tituloTarefa).sendAccessibilityEvent(
//            AccessibilityEvent.TYPE_VIEW_FOCUSED)
//
//        multiplasContasListener.clickBotaoExibirLista()
//    }
//
//    private fun recolheListaDeContas() {
//        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_top_with_fade)
//        animation.duration = ANIMATE_DURATION.toLong()
//        animation.fillAfter = true
//        animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {
//                viewRoot.visibility = View.VISIBLE
//            }
//
//            override fun onAnimationEnd(animation: Animation) {
//                viewRoot.visibility = View.GONE
//            }
//
//            override fun onAnimationRepeat(animation: Animation) {
//                // Não é usado
//            }
//        })
//        viewAccountContent.startAnimation(animation)
//
//        animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
//        animation.duration = ANIMATE_DURATION.toLong()
//        animation.fillAfter = true
//        viewOverlay.startAnimation(animation)
//        multiplasContasListener.clickBotaoRecolherLista()
//    }

    companion object {
        private const val ANIMATE_DURATION = 500
    }
}