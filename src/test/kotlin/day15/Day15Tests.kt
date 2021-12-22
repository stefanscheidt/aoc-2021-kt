package day15

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day15Tests {

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 40
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 315
    }

    private val sample = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent()
}
