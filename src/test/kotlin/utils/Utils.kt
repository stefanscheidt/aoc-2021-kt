package utils

import java.io.File
import java.nio.charset.StandardCharsets

fun solvePuzzle(day: Int, solver: (Sequence<String>) -> Long): Long =
    File("./input/day$day.txt").useLines(StandardCharsets.UTF_8, solver)
