package day14

import utils.solvePuzzle

// part 1, does not work for part 2

typealias Polymer = String

typealias InsertionRules = Map<Polymer, Char>

fun Polymer.insertElement(element: Char): Polymer =
    "${first()}$element${last()}"

fun Polymer.insertElements(rules: InsertionRules): Polymer =
    windowed(2)
        .map { p -> rules[p]?.let { p.insertElement(it) } ?: p }
        .map { p -> p.dropLast(1) }
        .plus(last())
        .joinToString("")

fun parseInput1(input: List<String>): Pair<Polymer, InsertionRules> {
    val template = input.first()
    val rules = input.drop(2).associate {
        val parts = it.split(" -> ")
        parts[0] to parts[1].first()
    }
    return Pair(template, rules)
}

fun InsertionRules.generatePolymer(template: Polymer, iterations: Int) =
    generateSequence(template) { it.insertElements(this) }.take(iterations + 1).last()

fun solvePuzzle1(input: Sequence<String>): Int {
    val (template, rules) = parseInput1(input.toList())
    val occurrences = rules.generatePolymer(template, 10)
        .groupingBy { it }
        .eachCount()
        .values
    val max = occurrences.maxOrNull() ?: 0
    val min = occurrences.minOrNull() ?: 0
    return max - min
}

// part 2, would also solve part 1

typealias ElementPair = Pair<Char, Char>

typealias ElementInsertionRules = Map<ElementPair, Char>

typealias ElementFrequencies = Map<ElementPair, Long>

fun <T> MutableMap<T, Long>.increment(key: T, amount: Long) {
    put(key, getOrDefault(key, 0L) + amount)
}

fun ElementFrequencies.updateWith(rules: ElementInsertionRules /* = kotlin.collections.Map<kotlin.String, kotlin.Char> */): ElementFrequencies {
    val next = mutableMapOf<ElementPair, Long>()
    forEach { (pair, count) ->
        val element = rules[pair]
        if (element == null) {
            next[pair] = count
        } else {
            next.increment(ElementPair(pair.first, element), count)
            next.increment(ElementPair(element, pair.second), count)
        }
    }
    return next
}

fun ElementFrequencies.charFrequencies(last: Char): Map<Char, Long> =
    map { (pair, count) -> pair.first to count }
        .groupBy({ (char, _) -> char }, { (_, count) -> count })
        .mapValues { (char, counts) -> if (char == last) counts.sum() + 1L else counts.sum() }

fun elementPair(pair: String): ElementPair =
    Pair(pair[0], pair[1])

data class InputData(
    val frequencies: ElementFrequencies,
    val rules: ElementInsertionRules, /* = kotlin.collections.Map<day14.ElementPair /* = kotlin.Pair<kotlin.Char, kotlin.Char> */, kotlin.Char> */
    val lastChar: Char,
)

fun parseFrequencies(input: String): ElementFrequencies {
    val frequencies = mutableMapOf<ElementPair, Long>()
    input.windowed(2).forEach {
        frequencies.increment(ElementPair(it[0], it[1]), 1L)
    }
    return frequencies
}

fun parseRules(input: List<String>): ElementInsertionRules =
    input.associate {
        val parts = it.split(" -> ")
        elementPair(parts[0]) to parts[1].first()
    }

fun parseInput2(input: List<String>): InputData {
    val template = input.first()
    val rules = input.drop(2)
    return InputData(
        frequencies = parseFrequencies(template),
        rules = parseRules(rules),
        lastChar = template.last()
    )
}

fun ElementInsertionRules.computeFrequencies(seed: ElementFrequencies, iterations: Int) =
    generateSequence(seed) { it.updateWith(this) }.take(iterations + 1).last()

fun solvePuzzle2(input: Sequence<String>): Long {
    val (seed, rules, lastChar) = parseInput2(input.toList())
    val occurrences = rules.computeFrequencies(seed, 40)
        .charFrequencies(lastChar)
        .values
    val max = occurrences.maxOrNull() ?: 0L
    val min = occurrences.minOrNull() ?: 0L
    return max - min
}

fun main() {
    println("Answer 1: ${solvePuzzle(14, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(14, ::solvePuzzle2)}")
}
