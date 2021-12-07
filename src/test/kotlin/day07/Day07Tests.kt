package day07

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day07Tests {

    @Test
    fun `calculate median`() {
        median(listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)) shouldBe 2
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 37L
    }

    @Test
    @Disabled
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 0L
    }

    private val sample = """
        16,1,2,0,4,2,7,1,2,14
    """.trimIndent()
}
