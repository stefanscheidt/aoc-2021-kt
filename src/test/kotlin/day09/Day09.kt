package day09

import utils.solvePuzzle

data class Point(val x: Int, val y: Int)

fun Point.neighbours(): Set<Point> =
    setOf(this, copy(x = x - 1), copy(x = x + 1), copy(y = y - 1), copy(y = y + 1))

typealias Heightmap = List<List<Int>>

operator fun Heightmap.get(p: Point) =
    this[p.y][p.x]

fun Heightmap.getOrNull(p: Point): Int? =
    this.getOrNull(p.y)?.getOrNull(p.x)

private fun Heightmap.points(): Set<Point> =
    indices.flatMap { y -> this[0].indices.map { x -> Point(x, y) } }.toSet()

fun Heightmap.neighbourValues(p: Point): Set<Int> {
    return p.neighbours().mapNotNull(::getOrNull).toSet()
}

fun Heightmap.isLowPoint(p: Point): Boolean =
    neighbourValues(p).minOrNull() == getOrNull(p)

fun Heightmap.basinOf(p: Point): Set<Point> {
    val higherNeighbours = p.neighbours().filter {
        getOrNull(it) != null && get(it) != 9 && get(it) > get(p)
    }
    return (higherNeighbours + p + higherNeighbours.flatMap(::basinOf)).toSet()
}

fun parseHeightmap(input: List<String>): Heightmap =
    input.map { it.map(Char::digitToInt) }

fun solvePuzzle1(input: Sequence<String>): Long {
    val map = parseHeightmap(input.toList())
    return map.points()
        .filter { p -> map.isLowPoint(p) }
        .sumOf { p -> 1 + map[p] }
        .toLong()
}

fun solvePuzzle2(input: Sequence<String>): Long {
    val map = parseHeightmap(input.toList())
    @Suppress("ConvertCallChainIntoSequence")
    return map.points()
        .filter { p -> map.isLowPoint(p) }
        .map { p -> map.basinOf(p) }
        .sortedByDescending { it.size }
        .take(3)
        .fold(1) { acc, points -> acc * points.size }
        .toLong()
}

fun main() {
    println("Answer 1: ${solvePuzzle(9, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(9, ::solvePuzzle2)}")
}
