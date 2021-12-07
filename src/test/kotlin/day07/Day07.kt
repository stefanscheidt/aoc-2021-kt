package day07

import utils.solvePuzzle
import kotlin.math.abs

fun median(ns: List<Int>): Int {
    val sorted = ns.sorted()
    return if (ns.size % 2 == 0) {
        (sorted[ns.size / 2 - 1] + sorted[ns.size / 2]) / 2
    } else {
        sorted[ns.size / 2]
    }
}

fun solvePuzzle1(input: Sequence<String>): Long {
    val ints = input.first().split(",").map(String::toInt)
    val median = median(ints)
    val fuel = ints.sumOf { abs(it - median) }
    return fuel.toLong()
}

fun sumOfFuel(from: List<Int>, to: Int): Int =
    from.map { abs(it - to) }.sumOf { it * (it + 1) / 2 }

fun solvePuzzle2(input: Sequence<String>): Long {
    val ints = input.first().split(",").map(String::toInt)
    // val average = ints.average().roundToInt()
    // val fuel = ints.map { abs(it - average) }.sumOf { it * (it + 1) / 2 }
    val min = ints.minOrNull() ?: 0
    val max = ints.maxOrNull() ?: 0
    val fuel = (min..max).minOfOrNull { sumOfFuel(ints, it) } ?: 0
    return fuel.toLong()
}

fun main() {
    println("Answer 1: ${solvePuzzle(7, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(7, ::solvePuzzle2)}")
}
