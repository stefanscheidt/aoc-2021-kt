package day03

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

typealias Word = List<Int>

private fun Sequence<String>.toWords(): List<Word> =
    map { it.map(Char::digitToInt) }.toList()

// part 1

fun solvePuzzle1(input: Sequence<String>): Int {
    val words = input.toWords()
    val zeros = List(words.size) { 0 }
    val counts = words.fold(zeros) { acc, word -> acc.zip(word).map(::sum) }
    val gamma = counts.rate { it >= words.size / 2.0 }
    val epsilon = counts.rate { it < words.size / 2.0 }
    return gamma * epsilon
}

private fun List<Int>.rate(p: (Int) -> Boolean): Int =
    joinToString("") { if (p(it)) "1" else "0" }.toInt(2)

private fun sum(pair: Pair<Int, Int>): Int =
    pair.first + pair.second

// part 2

typealias BitCriteria = (List<Word>, Int) -> Boolean

internal fun countBits(words: List<Word>, index: Int): Int =
    words.fold(0) { acc, word -> acc + word[index] }

internal fun applyCriteria(words: List<Word>, index: Int, criteria: BitCriteria): Int =
    if (criteria(words, countBits(words, index))) 1 else 0

internal fun filter(words: List<Word>, index: Int, criteria: BitCriteria): List<Word> {
    val expected = applyCriteria(words, index, criteria)
    return words.filter { word -> word[index] == expected }
}

internal fun rating(words: List<Word>, criteria: BitCriteria): Int {
    tailrec fun go(words: List<Word>, index: Int): Word {
        val filtered = filter(words, index, criteria)
        return if (filtered.size == 1) filtered[0] else go(filtered, index + 1)
    }

    return go(words, 0).joinToString("") { it.toString() }.toInt(2)
}

val mostCommon: BitCriteria = { words, count -> count >= words.size / 2.0 }
val leastCommon: BitCriteria = { words, count -> count < words.size / 2.0 }

fun solvePuzzle2(input: Sequence<String>): Int {
    val words = input.toWords()
    val ogr = rating(words, mostCommon)
    val csr = rating(words, leastCommon)
    return (ogr * csr)
}

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

    // part 1

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 198
    }

    @Test
    fun `solve first puzzle`() {
        solvePuzzle(3, ::solvePuzzle1) shouldBe 741950
    }

    // part 2

    @Test
    internal fun `count bits`() {
        val words = listOf(
            listOf(1, 0, 0),
            listOf(1, 1, 0),
            listOf(0, 1, 1),
        )

        countBits(words, 0) shouldBe 2
        countBits(words, 1) shouldBe 2
        countBits(words, 2) shouldBe 1
    }

    @Test
    internal fun `bit criteria`() {
        val words = listOf(
            listOf(1, 0, 0),
            listOf(1, 1, 0),
            listOf(0, 1, 1),
        )

        applyCriteria(words, 0, mostCommon) shouldBe 1
        applyCriteria(words, 1, mostCommon) shouldBe 1
        applyCriteria(words, 2, mostCommon) shouldBe 0

        applyCriteria(words, 0, leastCommon) shouldBe 0
        applyCriteria(words, 1, leastCommon) shouldBe 0
        applyCriteria(words, 2, leastCommon) shouldBe 1
    }

    @Test
    internal fun `filter words`() {
        val words = listOf(
            listOf(1, 0, 0),
            listOf(1, 1, 0),
            listOf(0, 1, 1),
        )

        filter(words, 0, mostCommon) shouldBe listOf(listOf(1, 0, 0), listOf(1, 1, 0))
    }

    @Test
    internal fun `rate words`() {
        val words = sample.lineSequence().toWords()

        rating(words, mostCommon) shouldBe 23
        rating(words, leastCommon) shouldBe 10
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 230
    }

    @Test
    fun `solve second puzzle`() {
        solvePuzzle(3, ::solvePuzzle2) shouldBe 903810
    }
}
