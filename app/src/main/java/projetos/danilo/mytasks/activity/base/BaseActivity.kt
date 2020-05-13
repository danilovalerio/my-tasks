package projetos.danilo.mytasks.activity.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

//open é necessário, pois por padrão toda classe kotlin é private
open class BaseActivity : AppCompatActivity() {

    protected fun configurarToolbar(toolbar: Toolbar, tituloIdResource: Int, exibirBotaoVoltar: Boolean = false){
        toolbar.title = getString(tituloIdResource)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(exibirBotaoVoltar)
    }
}
