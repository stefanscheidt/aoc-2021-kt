package day05

import utils.solvePuzzle
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

data class Pos(val x: Int, val y: Int)

/** line segment for first puzzle */
fun seg1(p1: Pos, p2: Pos): List<Pos> =
    when {
        p1.x == p2.x -> (min(p1.y, p2.y)..max(p1.y, p2.y)).map { y -> Pos(p1.x, y) }
        p1.y == p2.y -> (min(p1.x, p2.x)..max(p1.x, p2.x)).map { x -> Pos(x, p1.y) }
        else -> emptyList()
    }

/** line segment for second puzzle */
fun seg2(p1: Pos, p2: Pos): List<Pos> =
    when {
        p1.x == p2.x -> seg1(p1, p2)
        p1.y == p2.y -> seg1(p1, p2)
        abs(p2.x - p1.x) == abs(p2.y - p1.y) -> diag(p1, p2)
        else -> emptyList()
    }

fun diag(p1: Pos, p2: Pos): List<Pos> {
    val len = abs(p2.x - p1.x)
    val vec = Pos((p2.x - p1.x) / len, (p2.y - p1.y) / len)
    return (0..len).map { inc -> Pos(p1.x + inc * vec.x, p1.y + inc * vec.y) }
}

fun toSeg(s: String, seg: (Pos, Pos) -> List<Pos>): List<Pos> {
    val positions = s.split(" -> ").map { part ->
        val coordinates = part.split(",").map(String::toInt)
        Pos(coordinates[0], coordinates[1])
    }
    return seg(positions[0], positions[1])
}

fun solve(input: Sequence<String>, seg: (Pos, Pos) -> List<Pos>): Long {
    val map = mutableMapOf<Pos, Int>()
    input.map { row -> toSeg(row, seg) }.forEach { segment ->
        segment.forEach { pos -> map.compute(pos) { _, c -> (c ?: 0) + 1 } }
    }
    return map.count { it.value > 1 }.toLong()
}

fun solvePuzzle1(input: Sequence<String>): Long =
    solve(input, ::seg1)

fun solvePuzzle2(input: Sequence<String>): Long =
    solve(input, ::seg2)

fun main() {
    println("Answer 1: ${solvePuzzle(5, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(5, ::solvePuzzle2)}")
}
