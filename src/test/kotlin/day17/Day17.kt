package day17

import utils.solvePuzzle
import kotlin.math.sign

data class Point(val x: Int, val y: Int)

fun P(x: Int, y: Int): Point =
    Point(x, y)

typealias Vector = Point

fun v(x: Int, y: Int): Vector =
    Vector(x, y)

operator fun Point.plus(v: Vector /* = day17.Point */): Point =
    copy(x = x + v.x, y = y + v.y)

data class Area(val xRange: IntRange, val yRange: IntRange)

operator fun Area.contains(p: Point): Boolean =
    p.x in xRange && p.y in yRange

fun Point.missed(targetArea: Area): Boolean =
    x > targetArea.xRange.last || y < targetArea.yRange.first

fun Point.trace(vector: Vector, targetArea: Area): Sequence<Point> =
    generateSequence(Pair(this, vector)) { (p, v) ->
        if ((p + v).missed(targetArea)) {
            null
        } else {
            val dv = v((-1) * v.x.sign, -1)
            Pair(p + v, v + dv)
        }
    }.map { it.first }

fun parseTargetArea(input: String): Area {
    fun toIntRange(s: String): IntRange {
        val parts = s.split("..")
        return (parts[0].toInt()..parts[1].toInt())
    }

    val ranges = input.substringAfter("target area: ").split(", ")
    return Area(
        toIntRange(ranges[0].substringAfter("x=")),
        toIntRange(ranges[1].substringAfter("y=")),
    )
}

fun solvePuzzle1(input: Sequence<String>): Int {
    val targetArea = parseTargetArea(input.first())
    val minY = targetArea.yRange.first
    return minY * (minY + 1) / 2
}

fun solvePuzzle2(input: Sequence<String>): Int {
    val origin = P(0, 0)
    val targetArea = parseTargetArea(input.first())

    val minVx = 0
    val maxVx = targetArea.xRange.last

    val minVy = targetArea.yRange.first
    val maxVy = minVy * (minVy + 1) / 2

    return (minVx..maxVx).sumOf { x ->
        (minVy..maxVy).count { y ->
            origin.trace(v(x, y), targetArea).last() in targetArea
        }
    }
}

fun main() {
    println("Answer 1: ${solvePuzzle(17, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(17, ::solvePuzzle2)}")
}
