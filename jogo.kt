import kotlin.system.exitProcess
import kotlin.concurrent.thread
import java.util.concurrent.atomic.AtomicLong

// Singleton para criar mapas (usando object conforme solicitado)
object MapaFactory {
    private val mapas = listOf(
        """
        █████████████
        █S         ██
        █████ ███ ███
        █     █   ███
        █ ███ █ █ ███
        █ █   █ █   █
        █ █ ███ ███ █
        █ █         █
        █ █████████ █
        █           █
        ███████████E█
        """.trimIndent(),

        """
        ███████████████
        █S  █ █   █  █
        █ █ █ █ █ █ ██
        █ █   █ █   ██
        █ █████ ██████
        █             █
        █████ ███████ █
        █     █     █ █
        █ █████ ███ █ █
        █ █     █   █ █
        █ █ ███████ █ █
        █ █         █ █
        █ ███████████ █
        █            E█
        ███████████████
        """.trimIndent(),

        """
        █████████████████
        █S              █
        █ █████████████ █
        █ █           █ █
        █ █ ███████ █ █ █
        █ █ █     █ █ █ █
        █ █ █ █ █ █ █ █ █
        █ █ █ █ █ █ █ █ █
        █ █   █   █   █ █
        █ █████████████ █
        █ █             █
        █ ███████████████
        █               █
        ███████████████ █
        █              E█
        █████████████████
        """.trimIndent()
    )

    private var mapaAtualIndex = 0

    fun getProximoMapa(): Pair<Array<CharArray>, Pair<Int, Int>> {
        if (mapaAtualIndex >= mapas.size) {
            throw IllegalStateException("Todos os mapas foram completados!")
        }

        val mapaString = mapas[mapaAtualIndex]
        val linhas = mapaString.lines()
        val labirinto = Array(linhas.size) { i -> linhas[i].toCharArray() }

        var inicio = Pair(0, 0)
        var saida = Pair(0, 0)

        for (i in labirinto.indices) {
            for (j in labirinto[i].indices) {
                when (labirinto[i][j]) {
                    'S' -> inicio = Pair(i, j)
                    'E' -> saida = Pair(i, j)
                }
            }
        }

        mapaAtualIndex++
        return Pair(labirinto, inicio)
    }

    fun getTotalMapas(): Int = mapas.size
    fun getMapaAtual(): Int = mapaAtualIndex
    fun temProximoMapa(): Boolean = mapaAtualIndex < mapas.size
}

// Classe principal do jogo
class JogoLabirinto {
    private var labirinto: Array<CharArray> = arrayOf()
    private var posicaoJogador: Pair<Int, Int> = Pair(0, 0)
    private var tempoInicio: Long = 0
    private var tempoTotal: AtomicLong = AtomicLong(0)
    private var threadTempo: Thread? = null

    fun iniciarJogo() {
        println("=== JOGO DE LABIRINTO ===")
        println("Instruções:")
        println("• Use W (cima), A (esquerda), S (baixo), D (direita) para mover")
        println("• S = Início, E = Saída, █ = Parede")
        println("• Complete todos os ${MapaFactory.getTotalMapas()} labirintos para vencer!")
        println("• Pressione 'Q' para sair do jogo")
        println("\nPressione Enter para começar...")
        readLine()

        iniciarContadorTempo()
        carregarProximoMapa()
    }

    private fun iniciarContadorTempo() {
        tempoInicio = System.currentTimeMillis()
        threadTempo = thread(start = true) {
            while (true) {
                Thread.sleep(1000)
                if (!MapaFactory.temProximoMapa()) {
                    break
                }
                tempoTotal.set(System.currentTimeMillis() - tempoInicio)
            }
        }
    }

    private fun carregarProximoMapa() {
        try {
            val (novoLabirinto, inicio) = MapaFactory.getProximoMapa()
            this.labirinto = novoLabirinto
            this.posicaoJogador = inicio

            println("\n=== LABIRINTO ${MapaFactory.getMapaAtual()}/${MapaFactory.getTotalMapas()} ===")
            println("Tempo: ${formatarTempo(tempoTotal.get())}")
            exibirLabirinto()
            aguardarMovimento()
        } catch (e: IllegalStateException) {
            finalizarJogo(true)
        }
    }

    private fun exibirLabirinto() {
        val labirintoCopy = labirinto.map { it.copyOf() }.toTypedArray()
        val (x, y) = posicaoJogador
        labirintoCopy[x][y] = 'P'

        println()
        labirintoCopy.forEach { linha ->
            println(linha.joinToString("") {
                when (it) {
                    '█' -> "██"
                    'S' -> "S "
                    'E' -> "E "
                    'P' -> "P "
                    else -> "  "
                }
            })
        }
        println()
    }

    private fun aguardarMovimento() {
        while (true) {
            print("Digite sua movimentação (W/A/S/D): ")
            val input = readLine()?.uppercase()?.getOrNull(0) ?: continue

            when (input) {
                'W' -> mover(-1, 0)
                'S' -> mover(1, 0)
                'A' -> mover(0, -1)
                'D' -> mover(0, 1)
                'Q' -> {
                    println("\nSaindo do jogo...")
                    exitProcess(0)
                }
                else -> {
                    println("Movimento inválido! Use W, A, S ou D.")
                    continue
                }
            }

            exibirLabirinto()

            if (verificarVitoria()) {
                if (MapaFactory.temProximoMapa()) {
                    println("Parabéns! Você encontrou a saída!")
                    println("Próximo labirinto carregando...")
                    Thread.sleep(2000)
                    carregarProximoMapa()
                } else {
                    finalizarJogo(true)
                }
                break
            }
        }
    }

    private fun mover(dx: Int, dy: Int) {
        val (x, y) = posicaoJogador
        val novoX = x + dx
        val novoY = y + dy

        if (novoX in labirinto.indices && novoY in labirinto[0].indices) {
            when (labirinto[novoX][novoY]) {
                '█' -> {
                    println("Você bateu em uma parede!")
                    return
                }
                ' ', 'S', 'E' -> {
                    posicaoJogador = Pair(novoX, novoY)
                    println("➡Movendo para ($novoX, $novoY)")
                }
            }
        } else {
            println("Movimento inválido! Você não pode sair do labirinto.")
        }
    }

    private fun verificarVitoria(): Boolean {
        val (x, y) = posicaoJogador
        return labirinto[x][y] == 'E'
    }

    private fun finalizarJogo(vitoria: Boolean) {
        threadTempo?.interrupt()
        val tempoFinal = tempoTotal.get()

        println("\n" + "=".repeat(50))
        if (vitoria) {
            println("PARABÉNS!")
            println("Você completou todos os ${MapaFactory.getTotalMapas()} labirintos!")
            println("Tempo total: ${formatarTempo(tempoFinal)}")
        } else {
            println("Game Over!")
        }
        println("=".repeat(50))
        exitProcess(0)
    }

    private fun formatarTempo(millis: Long): String {
        val segundos = millis / 1000
        val minutos = segundos / 60
        val segundosRestantes = segundos % 60
        return String.format("%02d:%02d", minutos, segundosRestantes)
    }
}

// Função main para executar o jogo
fun main() {
    val jogo = JogoLabirinto()
    jogo.iniciarJogo()
}
