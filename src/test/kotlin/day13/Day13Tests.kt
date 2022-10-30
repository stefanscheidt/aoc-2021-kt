package day13

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Tests {

    @Test
    internal fun `fold value`() {
        4.fold(5) shouldBe 4
        6.fold(5) shouldBe 4
    }

    @Test
    internal fun `fold point`() {
        Point(6, 10).fold(Left(5)) shouldBe Point(4, 10)
        Point(6, 10).fold(Up(7)) shouldBe Point(6, 4)
    }

    @Test
    internal fun `parse origami`() {
        val input = """
            6,10
            0,14
            
            fold along y=7
            fold along x=5
        """.trimIndent().lineSequence()

        parseOrigami(input) shouldBe Pair(
            setOf(Point(6, 10), Point(0, 14)),
            listOf(Up(7), Left(5)),
        )
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 17
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 16
    }

    private val sample = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0
        
        fold along y=7
        fold along x=5
    """.trimIndent()
}
