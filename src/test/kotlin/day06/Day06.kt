package day06

import utils.solvePuzzle

typealias Evolution = Map<Int, Long>

fun evolutionOf(seed: List<Int>): Evolution {
    val evolution = mutableMapOf<Int, Long>()
    (0..8).forEach { timer -> evolution[timer] = seed.count { it == timer }.toLong() }
    return evolution
}

fun Evolution.tick(): Evolution {
    val next = mutableMapOf<Int, Long>()
    forEach { (timer, _) -> next[timer] = if (timer == 6) this[7]!! + this[0]!! else this[(timer + 1) % 9]!! }
    return next
}

fun solve(input: Sequence<String>, generations: Int): Long {
    val timers = input.first().split(",").map(String::toInt)
    var evolution = evolutionOf(timers)
    repeat(generations) { evolution = evolution.tick() }
    return evolution.entries.sumOf { it.value }
}

fun solvePuzzle1(input: Sequence<String>): Long =
    solve(input, 80)

fun solvePuzzle2(input: Sequence<String>): Long =
    solve(input, 256)

fun main() {
    println("Answer 1: ${solvePuzzle(6, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(6, ::solvePuzzle2)}")
}
