package day06

import utils.solvePuzzle

data class Fish(private var timer: Int = 8) {
    val time: Int
        get() = timer

    fun tick(): List<Fish> {
        timer -= 1
        if (timer < 0) {
            timer = 6
            return listOf(Fish())
        }
        return emptyList()
    }
}

private fun solve(input: Sequence<String>, times: Int): Long {
    val state = input.first().split(",").map(String::toInt).map(::Fish).toMutableList()
    repeat(times) {
        val newFish = mutableListOf<Fish>()
        state.forEach { fish -> newFish.addAll(fish.tick()) }
        state.addAll(newFish)
    }
    return state.size.toLong()
}

fun solvePuzzle1(input: Sequence<String>): Long =
    solve(input, 80)

fun solvePuzzle2(input: Sequence<String>): Long =
    solve(input, 256)

fun main() {
    println("Answer 1: ${solvePuzzle(6, ::solvePuzzle1)}")
    // println("Answer 2: ${solvePuzzle(6, ::solvePuzzle2)}")
}
