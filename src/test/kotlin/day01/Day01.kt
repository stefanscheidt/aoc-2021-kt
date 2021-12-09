package day01

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

fun main() {
    println("answer 1: ${solvePuzzle(1, ::solveFirstPuzzle)}")
    println("answer 2: ${solvePuzzle(1, ::solveSecondPuzzle)}")
}
