fun main(args: Array<String>) {

    var nome: String = "Luiz"
    var idade: Int = 22
    var salario: Double = 2300.0
    var meses: Int = 6
    var produtos: Int = 12

    var salAnual = salario * meses
    var salAnualLiquido = salAnual-400

    println("Olá $nome, seu salário será $salAnualLiquido! Seja responsável e gaste com conciência (compre skins)")

    println("nome: $nome")
    println("salario: $salario")
    println("idade: $idade")
    println("meses trabalhados: $meses")
    println("Produtos: $produtos")
    println("salario anual: $salAnual")

    

}
