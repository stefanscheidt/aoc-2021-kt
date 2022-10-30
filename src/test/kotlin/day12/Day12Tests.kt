package day12

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Tests {

    @Test
    internal fun `parse cave map`() {
        val input =
            """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
            """.trimIndent()

        parseCaveMap(input.lines()) shouldBe mapOf(
            "start" to setOf("A", "b"),
            "A" to setOf("start", "b", "c", "end"),
            "b" to setOf("start", "A", "d", "end"),
            "c" to setOf("A"),
            "d" to setOf("b"),
            "end" to setOf("A", "b"),
        )
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 226
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 3509
    }

    private val sample = """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent()
}
