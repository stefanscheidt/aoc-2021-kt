package day03

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

fun solvePuzzle1(input: Sequence<String>): Long {
    val bits = input.map { it.map(Char::digitToInt) }.toList()
    val zeros = List(bits.size) { 0 }
    val counts = bits.fold(zeros) { acc, row -> row.zip(acc).map(::sum) }
    val gamma = counts.rate { it > bits.size / 2 }
    val epsilon = counts.rate { !(it > bits.size / 2) }
    return (gamma * epsilon).toLong()
}

private fun List<Int>.rate(p: (Int) -> Boolean): Int =
    joinToString("") { if (p(it)) "1" else "0" }.toInt(2)

private fun sum(pair: Pair<Int, Int>): Int =
    pair.first + pair.second

class Day03Tests {

    private val sample = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 198L
    }

    @Test
    fun `solve first puzzle`() {
        solvePuzzle(3, ::solvePuzzle1) shouldBe 741950L
    }
}
