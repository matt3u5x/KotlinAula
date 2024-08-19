fun main(args: Array<String>) {

    var nomeProduto: String = "Teclado"
    var precoUnitario: Int = 350
    var quantidadeCompra: Int = 7
    var imposto: Double = 25.0
    var margemLucroDesejada: Double = 75.0

    var valorTotalSemImposto = precoUnitario * quantidadeCompra
    var valorTocalComImposto = valorTotalSemImposto + (valorTotalSemImposto * (imposto / 100))

    val precoSugeridoLucro = precoUnitario + (precoUnitario * (margemLucroDesejada / 100))


    

}
