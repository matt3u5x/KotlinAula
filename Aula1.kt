var version = 54353453

fun main(args: Array<String>){

    //Variaveis mutaveis
    //Para isso uso a palavra var

    var idade = 30
    idade = 31

    //Char usa aspas simples '' e String usa aspas duplas "".

    //var nome_variavel: tipo = valor
    var nome: String = "Luiz"
    var valor: Double = 6.66
    var peso: Int = 90
    var ePessoa: Boolean = true
    var v: Double = 80.5


    println("Minha idade é: $idade e meu peso é $peso")
    println("Nome: $nome")


    //Variaveis Imutaveis
    //Para isso iso o val

    val pi = 3.14
    val percentualIcms: Int = 19
    val nomeEmpresa: String = "Unipar"

    println("Version ${version}")
    mudarVersion(50)
    println("Version ${version}")

    var total = somarValores(10,20)
    println("Total $total")



}

//Função void, sem retorno
// fun nome_funcao(parametro)
fun mudarVersion(versionChange: Int){
    version = 6

}

//Funcoes com retorno
fun somarValores(valorA: Int, valorB: Int): Int {

    return valorA + valorB

}
