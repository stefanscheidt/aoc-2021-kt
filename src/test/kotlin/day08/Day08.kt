package day08

import utils.solvePuzzle

typealias Pattern = Set<Char>
typealias Digit = Set<Char>

typealias InputRow = Pair<List<Pattern>, List<Digit>>

fun parseInputRow(s: String): InputRow {
    val parts = s.split("|")
    val patterns = parts[0].split(" ").map(String::toSet)
    val digits = parts[1].split(" ").map(String::toSet)
    return Pair(patterns, digits)
}

fun solvePuzzle1(input: Sequence<String>): Long =
    input.map(::parseInputRow)
        .sumOf { (_, digits) -> digits.count { it.size in setOf(2, 3, 4, 7) } }
        .toLong()

fun solvePuzzle2(input: Sequence<String>): Long =
    -1L

fun main() {
    println("Answer 1: ${solvePuzzle(8, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(8, ::solvePuzzle2)}")
}
