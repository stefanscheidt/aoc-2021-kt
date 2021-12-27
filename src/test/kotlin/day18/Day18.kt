package day18

import utils.solvePuzzle
import java.util.Stack
import kotlin.math.ceil

val atLeastOneDigit = """\d+""".toRegex()
val atLeastTwoDigits = """\d\d+""".toRegex()

data class SnailfishNumber(val value: String)

fun sfn(value: String): SnailfishNumber =
    SnailfishNumber(value)

val SnailfishNumber.magnitude: Int
    get() {
        val stack = Stack<Int>()
        value.forEach { c ->
            when {
                c.isDigit() -> {
                    stack.push(c.digitToInt())
                }
                c == ']' -> {
                    val right = stack.pop()
                    val left = stack.pop()
                    stack.push(3 * left + 2 * right)
                }
            }
        }
        return stack.pop()
    }

fun SnailfishNumber.indexOfExplodingPair(): Int? {
    var depth = 0
    value.withIndex().forEach {
        when (it.value) {
            '[' -> depth++
            ']' -> depth--
        }
        if (depth == 5) return it.index
    }
    return null
}

fun SnailfishNumber.explode(): SnailfishNumber {
    val pairStart = indexOfExplodingPair() ?: return this
    val pairEnd = value.substring(pairStart).indexOfFirst { it == ']' } + pairStart
    val pairNumbers = value.substring((pairStart + 1) until pairEnd).split(",").map { it.toInt() }

    val before = value.substring(0, pairStart)
    val nextBefore = atLeastOneDigit.findAll(before).lastOrNull()?.let {
        before.replaceRange(it.range, "${it.value.toInt() + pairNumbers[0]}")
    } ?: before

    val after = value.substring(pairEnd + 1)
    val nextAfter = atLeastOneDigit.find(after)?.let {
        after.replaceRange(it.range, "${it.value.toInt() + pairNumbers[1]}")
    } ?: after

    return SnailfishNumber("${nextBefore}0$nextAfter")
}

fun SnailfishNumber.split(): SnailfishNumber {
    return atLeastTwoDigits.find(value)?.let { matchResult ->
        val number = matchResult.value.toInt()
        val newPair = "[${number / 2},${ceil(number / 2.0).toInt()}]"
        SnailfishNumber(value.replaceRange(matchResult.range, newPair))
    } ?: this
}

fun SnailfishNumber.reduce(): SnailfishNumber =
    generateSequence(this) {
        val exploded = it.explode()
        if (exploded != it) exploded else it.split()
    }
        .zipWithNext()
        .takeWhile { it.first != it.second }
        .last().second

infix operator fun SnailfishNumber.plus(other: SnailfishNumber): SnailfishNumber =
    SnailfishNumber("[$value,${other.value}]").reduce()

fun List<SnailfishNumber>.sum(): SnailfishNumber =
    reduce { acc, sfn -> acc + sfn }

fun solvePuzzle1(input: Sequence<String>): Int =
    input.map(::sfn).toList().sum().magnitude

fun solvePuzzle2(input: Sequence<String>): Int =
    -1

fun main() {
    println("Answer 1: ${solvePuzzle(18, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(18, ::solvePuzzle2)}")
}
