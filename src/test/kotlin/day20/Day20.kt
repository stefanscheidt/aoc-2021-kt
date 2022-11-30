package day20

import utils.solvePuzzle

data class Pos(val x: Int, val y: Int)

fun Pos.withNeighbours(): Set<Pos> =
    setOf(
        copy(x = x - 1, y = y - 1),
        copy(y = y - 1),
        copy(x = x + 1, y = y - 1),
        copy(x = x - 1),
        this,
        copy(x = x + 1),
        copy(x = x - 1, y = y + 1),
        copy(y = y + 1),
        copy(x = x + 1, y = y + 1),
    )

typealias Pixel = Int

fun parsePixel(input: Char): Pixel =
    if (input == '#') 1 else 0

typealias Algorithm = List<Pixel>

fun parseAlgorithm(input: String): Algorithm =
    input.lines().joinToString(separator = "").map(::parsePixel)

typealias Image = List<List<Int>>

fun parseImage(input: List<String>): Image =
    input.map { row -> row.map(::parsePixel) }

operator fun Image.get(p: Pos): Int =
    this[p.y][p.x]

fun Image.valueAt(p: Pos, default: Int): Int =
    if (p.y in indices && p.x in first().indices) this[p] else default

fun Image.enhanceOnce(alg: Algorithm, outsideValue: Int = 0): Image {
    val width = this.first().size
    val height = this.size
    return (-1..height).map { y ->
        (-1..width).map { x ->
            val algIndex = Pos(x, y).withNeighbours()
                .map { this.valueAt(it, outsideValue) }
                .toInt()
            alg[algIndex]
        }
    }
}

fun Image.enhance(alg: Algorithm, depth: Int): Image {
    var image = this
    var outsideValue = 0
    repeat(depth) {
        image = image.enhanceOnce(alg, outsideValue)
        outsideValue = if (outsideValue == 0) alg[0b000000000] else alg[0b111111111]
    }
    return image
}

typealias Bits = List<Int>

fun Bits.toInt(): Int =
    reversed().foldIndexed(0) { index, acc, bit ->
        acc + (bit shl index)
    }

private fun doSolvePuzzle(input: List<String>, depth: Int): Int {
    val alg = parseAlgorithm(input.first())
    val image = parseImage(input.drop(2))
    return image.enhance(alg, depth).sumOf { it.sum() }
}

fun solvePuzzle1(input: Sequence<String>): Int =
    doSolvePuzzle(input.toList(), 2)

fun solvePuzzle2(input: Sequence<String>): Int =
    doSolvePuzzle(input.toList(), 50)

fun main() {
    println("Answer 1: ${solvePuzzle(20, ::solvePuzzle1)}")
    println("Answer 1: ${solvePuzzle(20, ::solvePuzzle2)}")
}
