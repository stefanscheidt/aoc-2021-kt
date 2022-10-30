package day11

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Tests {

    @Test
    internal fun `increase energy`() {
        val grid = parseGrid(
            """
            11111
            19991
            19191
            19991
            11111            
            """.trimIndent().lines(),
        )

        var tick = grid.tick()

        tick.first.toPrettyString() shouldBe
            """
            34543
            40004
            50005
            40004
            34543
            """.trimIndent()
        tick.second shouldBe 9

        tick = tick.first.tick()

        tick.first.toPrettyString() shouldBe
            """
            45654
            51115
            61116
            51115
            45654
            """.trimIndent()
        tick.second shouldBe 0
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 1656
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 195
    }

    private val sample = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent()
}
