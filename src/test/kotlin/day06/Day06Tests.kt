package day06

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Tests {

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 5934L
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 26984457539L
    }

    private val sample = """
        3,4,3,1,2
    """.trimIndent()
}
