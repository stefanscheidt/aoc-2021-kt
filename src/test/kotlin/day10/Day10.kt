package day10

import utils.solvePuzzle
import java.util.Stack

val brackets = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

val Char.isOpeningBracket: Boolean
    get() = this in brackets.keys

val Char.isClosingBracket: Boolean
    get() = this in brackets.values

val corruptionScores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

val Char.corruptionScore: Int
    get() = corruptionScores[this] ?: 0

val completionScores = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

val Char.completionScore: Int
    get() = completionScores[this] ?: 0

sealed class ParseResult
object Success : ParseResult()
data class Incomplete(val stack: Stack<Char>) : ParseResult()
data class Corrupted(val char: Char) : ParseResult()

fun parseLine(line: String): ParseResult {
    val stack = Stack<Char>()
    line.forEach { c ->
        when {
            c.isOpeningBracket -> stack.push(c)
            c.isClosingBracket -> when {
                stack.isEmpty() -> return Corrupted(c)
                (brackets[stack.peek()] != c) -> return Corrupted(c)
                else -> stack.pop()
            }
            else -> return Corrupted(c)
        }
    }
    return if (stack.isEmpty()) Success else Incomplete(stack)
}

fun Stack<Char>.completion(): List<Char> {
    val completion = mutableListOf<Char?>()
    while (!isEmpty()) completion.add(brackets[pop()])
    return completion.filterNotNull()
}

fun solvePuzzle1(input: Sequence<String>): Int =
    input.map(::parseLine)
        .mapNotNull { result -> (result as? Corrupted)?.char }
        .sumOf { char -> char.corruptionScore }

fun solvePuzzle2(input: Sequence<String>): Int {
    val scores = input.map(::parseLine)
        .mapNotNull { result -> result as? Incomplete }
        .map { incomplete -> incomplete.stack.completion() }
        .map { parens -> parens.fold(0) { acc, char -> acc * 5 + char.completionScore } }
        .sorted()
        .toList()
    return scores.drop(scores.size / 2).first()
}

fun main() {
    println("Answer 1: ${solvePuzzle(10, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(10, ::solvePuzzle2)}")
}
