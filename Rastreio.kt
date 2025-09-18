class Pacote(val id: String, var status: StatusPacote) {
    fun processar() {
        status.acao(this)
    }

    fun atualizarStatus(novoStatus: StatusPacote) {
        status = novoStatus
    }
}

enum class StatusPacote(val acao: (Pacote) -> Unit) {
    RECEBIDO({ pacote ->
        println("Pacote ${pacote.id} recebido no centro de distribuição.")
        pacote.atualizarStatus(EM_TRANSITO)
        pacote.processar()
    }),
    EM_TRANSITO({ pacote ->
        println("Pacote ${pacote.id} está em transporte para o próximo destino.")
        pacote.atualizarStatus(ENTREGUE)
        pacote.processar()
    }),
    ENTREGUE({ pacote ->
        println("Pacote ${pacote.id} foi entregue ao destinatário.")
    }),
    PENDENTE({ pacote ->
        println("Pacote ${pacote.id} está aguardando análise.")
    });
}

fun main() {
    val pacote = Pacote("12345", StatusPacote.RECEBIDO)
    pacote.processar()
}
