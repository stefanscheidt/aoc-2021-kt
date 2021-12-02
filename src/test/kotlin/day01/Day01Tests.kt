package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

fun solveFirstPuzzle(input: Sequence<String>): Long =
    input.map(String::toLong)
        .increases()

fun solveSecondPuzzle(input: Sequence<String>): Long =
    input.map(String::toLong)
        .windowed(3)
        .map { it.sum() }
        .increases()

private fun Sequence<Long>.increases(): Long =
    zipWithNext()
        .count { it.first < it.second }
        .toLong()

class Day01Tests {

    @Test
    internal fun `solve first puzzle for sample`() {
        val input = sample.lineSequence()

        solveFirstPuzzle(input) shouldBe 7L
    }

    @Test
    internal fun `solve first puzzle`() {
        solvePuzzle(1, ::solveFirstPuzzle) shouldBe 1529L
    }

    @Test
    internal fun `solve second puzzle for sample`() {
        val input = sample.lineSequence()

        solveSecondPuzzle(input) shouldBe 5L
    }

    @Test
    internal fun `solve second puzzle`() {
        solvePuzzle(1) { solveSecondPuzzle(it) } shouldBe 1567L
    }
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
            263"""
    .trimIndent()
