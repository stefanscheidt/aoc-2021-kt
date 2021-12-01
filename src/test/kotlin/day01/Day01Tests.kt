package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

fun solveFirstPuzzle(input: Sequence<String>): Long =
    input.map(String::toLong)
        .zipWithNext()
        .fold(0L) { acc, pair ->
            if (pair.first < pair.second) acc + 1 else acc
        }

class Day01Tests {

    @Test
    internal fun `solve first sample`() {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263"""
            .trimIndent()
            .lineSequence()

        solveFirstPuzzle(input) shouldBe 7L
    }

    @Test
    internal fun `solve first puzzle`() {
        solvePuzzle(1) { solveFirstPuzzle(it) } shouldBe 1529L
    }
}
