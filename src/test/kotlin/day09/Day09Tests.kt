package day09

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Tests {

    @Test
    internal fun `parse height map`() {
        parseHeightmap(listOf("12", "34")) shouldBe listOf(listOf(1, 2), listOf(3, 4))
    }

    @Test
    internal fun `calculate neighbours`() {
        val map = listOf(
            listOf(11, 12, 13),
            listOf(21, 22, 23),
            listOf(31, 32, 33),
        )
        map.neighbourValues(Point(0, 0)) shouldBe setOf(11, 12, 21)
        map.neighbourValues(Point(1, 0)) shouldBe setOf(11, 12, 13, 22)
        map.neighbourValues(Point(1, 1)) shouldBe setOf(12, 21, 22, 23, 32)
    }

    @Test
    internal fun `test low point`() {
        val map = listOf(
            listOf(2, 1, 9),
            listOf(3, 9, 8),
        )
        map.isLowPoint(Point(0, 0)) shouldBe false
        map.isLowPoint(Point(1, 0)) shouldBe true
    }

    @Test
    internal fun `compute basin`() {
        val map = parseHeightmap(sample.lines())

        map.basinOf(Point(1, 0)) shouldBe setOf(Point(0, 0), Point(1, 0), Point(0, 1))
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 15
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 1134
    }

    private val sample = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent()
}
