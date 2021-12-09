package day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Tests {

    @Test
    internal fun `solve first puzzle for sample`() {
        val input = sample.lineSequence()

        solveFirstPuzzle(input) shouldBe 150L
    }

    @Test
    internal fun `solve second puzzle for sample`() {
        val input = sample.lineSequence()

        solveSecondPuzzle(input) shouldBe -1L
    }

    val sample = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent()
}
