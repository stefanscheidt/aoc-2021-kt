package day11

import utils.solvePuzzle

data class Point(val x: Int, val y: Int)

val Point.neighbours: Set<Point>
    get() = setOf(
        copy(x = x - 1, y = y - 1), copy(y = y - 1), copy(x = x + 1, y = y - 1),
        copy(x = x - 1), copy(x = x + 1),
        copy(x = x - 1, y = y + 1), copy(y = y + 1), copy(x = x + 1, y = y + 1),
    )

typealias Grid = Map<Point, Int>

val Grid.points: Set<Point>
    get() = keys

fun Int.flashes(): Boolean =
    this > 9

tailrec fun Grid.increaseEnergy(
    ps: Map<Point, Int> = points.associateWith { 1 },
    flashing: Set<Point> = emptySet()
): Pair<Grid, Set<Point>> =
    if (ps.isEmpty()) {
        Pair(this, emptySet())
    } else {
        val nextGrid = mapValues { (p, e) -> e + (ps[p] ?: 0) }
        val nextFlashing = nextGrid.filterValues(Int::flashes).points - flashing
        val flashed = nextFlashing.flatMap { it.neighbours }.groupingBy { it }.eachCount()
        nextGrid.increaseEnergy(flashed, flashing + nextFlashing)
    }

fun Grid.tick(): Pair<Grid, Int> {
    val increasedGrid = increaseEnergy().first
    return Pair(
        increasedGrid.mapValues { (_, e) -> if (e.flashes()) 0 else e },
        increasedGrid.count { (_, e) -> e.flashes() }
    )
}

fun Grid.evolution(): Sequence<Pair<Grid, Int>> =
    generateSequence(Pair(this, 0)) { (grid, flashes) ->
        val (nextGrid, nextFlashed) = grid.tick()
        Pair(nextGrid, flashes + nextFlashed)
    }

fun parseGrid(input: List<String>): Grid =
    input.flatMapIndexed { y, row ->
        row.trim().mapIndexed { x, c ->
            Point(x, y) to c.digitToInt()
        }
    }.toMap()

fun solvePuzzle1(input: Sequence<String>): Long =
    parseGrid(input.toList()).evolution().drop(100).first().second.toLong()

fun solvePuzzle2(input: Sequence<String>): Long {
    return parseGrid(input.toList()).evolution()
        .mapIndexed { index, (grid, _) -> Pair(index, grid) }
        .first { (_, grid) -> grid.values.all { it == 0 } }
        .first.toLong()
}

fun main() {
    println("Answer 1: ${solvePuzzle(11, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(11, ::solvePuzzle2)}")
}

// needed for tests

internal fun Grid.toPrettyString(): String {
    val xs = points.map { it.x }
    val minX = xs.minOrNull() ?: 0
    val maxX = xs.maxOrNull() ?: 0

    val ys = points.map { it.x }
    val minY = ys.minOrNull() ?: 0
    val maxY = ys.maxOrNull() ?: 0

    return (minY..maxY).joinToString(separator = "\n") { y ->
        (minX..maxX).joinToString(separator = "") { x ->
            get(Point(x, y))?.toString() ?: " "
        }
    }
}
