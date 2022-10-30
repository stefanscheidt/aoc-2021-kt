package day08

import utils.solvePuzzle

typealias Pattern = Set<Char>

typealias Digit = Set<Char>

typealias Note = Pair<List<Pattern>, List<Digit>>

fun parseNotes(s: String): Note {
    val parts = s.split("|")
    val patterns = parts[0].trim().split(" ").map(String::toSet)
    val digits = parts[1].trim().split(" ").map(String::toSet)
    return Pair(patterns, digits)
}

// Part 1

fun solvePuzzle1(input: Sequence<String>): Int =
    input.map(::parseNotes)
        .sumOf { (_, digits) -> digits.count { it.size in setOf(2, 3, 4, 7) } }

// Part 2

fun valueOf(patterns: List<Pattern>): Map<Pattern, Int> {
    infix fun Pattern.overlaps(other: Pattern?): Boolean =
        other == null || containsAll(other)

    fun List<Pattern>.ofSize(size: Int): List<Pattern> =
        filter { it.size == size }

    val patternFor = mutableMapOf<Int, Pattern>()

    // easy digits
    patternFor[1] = patterns.first { it.size == 2 }
    patternFor[4] = patterns.first { it.size == 4 }
    patternFor[7] = patterns.first { it.size == 3 }
    patternFor[8] = patterns.first { it.size == 7 }

    // pattern for 3 has size 5 and overlaps pattern for 1
    patternFor[3] = patterns.ofSize(5).first { it overlaps patternFor[1] }

    // pattern for 9 has size 6 and overlaps pattern for 3
    patternFor[9] = patterns.ofSize(6).first { it overlaps patternFor[3] }

    // pattern for 0 has size 6, overlaps pattern for 1 and 7, and is not pattern for 9
    patternFor[0] = patterns.ofSize(6)
        .filter { (it overlaps patternFor[1]) && (it overlaps patternFor[7]) }
        .first { it != patternFor[9] }

    // pattern for 6 has size 6 and is neither pattern for 0 nor pattern for 9
    patternFor[6] = patterns.ofSize(6).first { it != patternFor[0] && it != patternFor[9] }

    // pattern for 5 has size 5 and is overlapped by pattern for 6
    patternFor[5] = patterns.ofSize(5).first { patternFor[6]!! overlaps it }

    // pattern for 2 has size 5 and is neither pattern for 3 nor pattern for 5
    patternFor[2] = patterns.ofSize(5).first { it != patternFor[3] && it != patternFor[5] }

    return patternFor.entries.associate { (k, v) -> v to k }
}

fun solvePuzzle2(input: Sequence<String>): Int {
    operator fun Map<Pattern, Int>.invoke(pattern: Pattern?): Int =
        getValue(pattern!!)

    return input.map(::parseNotes).sumOf { (patterns, digits) ->
        val valueOf = valueOf(patterns)
        valueOf(digits[3]) + 10 * valueOf(digits[2]) + 100 * valueOf(digits[1]) + 1000 * valueOf(digits[0])
    }
}

fun main() {
    println("Answer 1: ${solvePuzzle(8, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(8, ::solvePuzzle2)}")
}
