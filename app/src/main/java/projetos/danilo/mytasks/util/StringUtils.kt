package projetos.danilo.mytasks.util

const val CHAR_DEFAULT = "-"

fun verificaTexto(texto: String) : String {
    if(texto.isNullOrBlank() || texto.isNullOrEmpty()){
        return CHAR_DEFAULT
    }
    return texto
}