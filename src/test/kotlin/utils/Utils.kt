package utils

import java.io.File
import java.nio.charset.StandardCharsets

fun solvePuzzle(day: Int, solver: (Sequence<String>) -> Long): Long =
    File("./input/day$day.txt").useLines(StandardCharsets.UTF_8, solver)

fun <T> List<List<T>>.transposed(): List<List<T>> {
    val transposed = MutableList(get(0).size) { MutableList<T?>(size) { null } }
    forEachIndexed { x, row ->
        row.forEachIndexed { y, cell ->
            transposed[y][x] = cell
        }
    }
    return transposed.map { row ->
        row.mapNotNull {
            it
        }
    }.toList()
}
