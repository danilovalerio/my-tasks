package projetos.danilo.mytasks.util

import android.app.Activity
import android.widget.Toast

fun Activity.toastShort(msg:String){
    Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
}

fun Activity.toastLong(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}