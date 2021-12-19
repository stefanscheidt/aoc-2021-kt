package day14

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@Suppress("SpellCheckingInspection")
class Day14Tests {

    @Test
    internal fun `insert polymers`() {
        val rules = mapOf("NN" to 'C', "NC" to 'B', "CB" to 'H')

        "NNCB".insertElements(rules) shouldBe "NCNBCHB"
    }

    @Test
    internal fun `update frequencies`() {
        val rules = mapOf(elementPair("NN") to 'C', elementPair("NC") to 'B', elementPair("CB") to 'H')
        val freqs = mapOf(elementPair("NN") to 1L, elementPair("NC") to 1L, elementPair("CB") to 1L)
        val next = freqs.updateWith(rules)

        // NCNBCHB
        next shouldBe mapOf(
            elementPair("NC") to 1L,
            elementPair("CN") to 1L,
            elementPair("NB") to 1L,
            elementPair("BC") to 1L,
            elementPair("CH") to 1L,
            elementPair("HB") to 1L,
        )

        // NCNBCHB
        next.charFrequencies('B') shouldBe mapOf(
            'N' to 2, 'C' to 2, 'B' to 2, 'H' to 1
        )
    }

    @Test
    internal fun `parse frequencies`() {
        parseFrequencies("SCVSC") shouldBe mapOf(
            elementPair("SC") to 2L,
            elementPair("CV") to 1L,
            elementPair("VS") to 1L,
        )
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 1588
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 2188189693529L
    }

    private val sample = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent()
}
