package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

fun solvePuzzle1(input: Sequence<String>): Int =
    input.map(String::toInt)
        .increases()

fun solvePuzzle2(input: Sequence<String>): Int =
    input.map(String::toInt)
        .windowed(3)
        .map { it.sum() }
        .increases()

private fun Sequence<Int>.increases(): Int =
    zipWithNext()
        .count { it.first < it.second }

class Day01Tests {

    @Test
    internal fun `solve first puzzle for sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 7
    }

    @Test
    internal fun `solve first puzzle`() {
        solvePuzzle(1, ::solvePuzzle1) shouldBe 1529
    }

    @Test
    internal fun `solve second puzzle for sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 5
    }

    @Test
    internal fun `solve second puzzle`() {
        solvePuzzle(1) { solvePuzzle2(it) } shouldBe 1567
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
