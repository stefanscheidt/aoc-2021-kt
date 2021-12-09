package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Tests {

    @Test
    internal fun `solve first puzzle for sample`() {
        val input = sample.lineSequence()

        solveFirstPuzzle(input) shouldBe 7L
    }

    @Test
    internal fun `solve second puzzle for sample`() {
        val input = sample.lineSequence()

        solveSecondPuzzle(input) shouldBe 5L
    }

    val sample = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
    """.trimIndent()
}
