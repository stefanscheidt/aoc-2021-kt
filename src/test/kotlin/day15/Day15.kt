package day15

import utils.solvePuzzle
import java.util.PriorityQueue

data class Point(val x: Int, val y: Int)

fun Point.neighbours(): Set<Point> =
    setOf(this, copy(x = x - 1), copy(x = x + 1), copy(y = y - 1), copy(y = y + 1))

data class WeightedPoint(val point: Point, val weight: Int) : Comparable<WeightedPoint> {
    override fun compareTo(other: WeightedPoint): Int =
        weight - other.weight
}

typealias Cave = List<List<Int>>

private fun traverse(destination: Point, riskOrNull: (point: Point) -> Int?): Int {
    val queue = PriorityQueue<WeightedPoint>()
        .also { it.add(WeightedPoint(Point(0, 0), 0)) }

    val visited = mutableSetOf<Point>()

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        if (current.point == destination) return current.weight

        if (current.point !in visited) {
            visited.add(current.point)
            val weightedNeighbours = current.point.neighbours()
                .filter { neighbour -> neighbour !in visited }
                .mapNotNull { neighbour ->
                    riskOrNull(neighbour)?.let { risk -> WeightedPoint(neighbour, risk + current.weight) }
                }
            queue.addAll(weightedNeighbours)
        }
    }

    error("No path to $destination found.")
}

fun parseCave(input: Sequence<String>): Cave =
    input.map { row -> row.map { cell -> cell.digitToInt() } }.toList()

fun solvePuzzle1(input: Sequence<String>): Int {
    val cave = parseCave(input)
    val destination = Point(cave.first().lastIndex, cave.lastIndex)
    return traverse(destination) { p -> cave.getOrNull(p.y)?.getOrNull(p.x) }
}

fun solvePuzzle2(input: Sequence<String>): Int {
    val cave = parseCave(input)
    val width = cave.first().size
    val height = cave.size
    val destination = Point(width * 5 - 1, height * 5 - 1)
    return traverse(destination) { p ->
        if (p.x > destination.x || p.y > destination.y) {
            null
        } else {
            cave.getOrNull(p.y % height)
                ?.getOrNull(p.x % width)
                ?.let { topLeftRisk -> (topLeftRisk + p.x / width + p.y / height - 1) % 9 + 1 }
        }
    }
}

fun main() {
    println("Answer 1: ${solvePuzzle(15, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(15, ::solvePuzzle2)}")
}
