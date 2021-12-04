package day04

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

class Day04Tests {

    @Test
    internal fun `find position`() {
        val card = cardOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )

        card.find(1) shouldBe Position(0, 0)
        card.find(6) shouldBe Position(1, 2)
        card.find(10) shouldBe null
    }

    @Test
    internal fun `mark a number`() {
        var card = bingoCardOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )

        card.marked shouldBe emptySet()

        card = card.mark(5)
        card.marked.shouldContainExactly(Position(1, 1))

        card = card.mark(8)
        card.marked.shouldContainExactly(Position(1, 1), Position(2, 1))

        card = card.mark(10)
        card.marked.shouldContainExactly(Position(1, 1), Position(2, 1))
    }

    @Test
    internal fun `check state`() {
        var card = bingoCardOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )

        card.solved shouldBe false

        card = card.mark(1).mark(5).mark(3)

        card.solved shouldBe false

        card = card.mark(2)

        card.solved shouldBe true
    }

    @Test
    internal fun `score a card`() {
        val card = bingoCardOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        ).mark(1).mark(4).mark(7)

        card.score(7) shouldBe 7 * (2 + 3 + 5 + 6 + 8 + 9)
    }

    @Test
    internal fun `list of string to card`() {
        val input = """
                        
            22 13 17 11  0
             8  2 23  4 24
            21  9 14 16  7
             6 10  3 18  5
             1 12 20 15 19
        """.trimIndent().lineSequence().toList()

        bingoCardOf(input) shouldBe bingoCardOf(
            listOf(22, 13, 17, 11, 0),
            listOf(8, 2, 23, 4, 24),
            listOf(21, 9, 14, 16, 7),
            listOf(6, 10, 3, 18, 5),
            listOf(1, 12, 20, 15, 19),
        )
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 4512L
    }

    @Test
    fun `solve first puzzle`() {
        solvePuzzle(4, ::solvePuzzle1) shouldBe 49686L
    }

    private val sample = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
        
        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
    """.trimIndent()
}
