package day13

import utils.solvePuzzle

sealed interface FoldInstruction {
    val line: Int
}

data class Up(override val line: Int) : FoldInstruction
data class Left(override val line: Int) : FoldInstruction

fun Int.fold(line: Int): Int =
    if (this < line) this else line - (this - line)

data class Point(val x: Int, val y: Int)

fun Point.fold(instruction: FoldInstruction): Point =
    when (instruction) {
        is Left -> copy(x = x.fold(instruction.line))
        is Up -> copy(y = y.fold(instruction.line))
    }

typealias Paper = Set<Point>

fun Paper.fold(instruction: FoldInstruction): Paper =
    map { it.fold(instruction) }.toSet()

typealias Origami = Pair<Paper, List<FoldInstruction>>

fun parseOrigami(input: Sequence<String>): Origami {
    fun parsePoint(input: String): Point {
        val parts = input.split(",")
        return Point(parts[0].toInt(), parts[1].toInt())
    }

    fun parseInstruction(input: String): FoldInstruction {
        val parts = input.split("=")
        val instruction = parts[0]
        val line = parts[1].toInt()
        return if (instruction.contains("fold along x")) Left(line) else Up(line)
    }

    val paper = mutableSetOf<Point>()
    val instructions = mutableListOf<FoldInstruction>()

    val iter = input.iterator()
    while (iter.hasNext()) {
        val next = iter.next()
        if (next.isEmpty()) break

        paper.add(parsePoint(next))
    }

    while (iter.hasNext()) {
        val next = iter.next()
        if (next.isEmpty()) break

        instructions.add(parseInstruction(next))
    }

    return Pair(paper, instructions)
}

fun solvePuzzle1(input: Sequence<String>): Int {
    val (paper, instructions) = parseOrigami(input)
    return paper.fold(instructions.first()).size
}

fun solvePuzzle2(input: Sequence<String>): Int {
    val (paper, instructions) = parseOrigami(input)
    val result = instructions.fold(paper) { acc, instr -> acc.fold(instr) }
    printPaper(result)
    return result.size
}

fun printPaper(paper: Paper) {
    val maxX = paper.maxByOrNull(Point::x)?.x ?: 0
    val maxY = paper.maxByOrNull(Point::y)?.y ?: 0
    println(
        (0..maxY).joinToString("\n") { y ->
            (0..maxX).joinToString("") { x ->
                if (paper.contains(Point(x, y))) "#" else "."
            }
        }
    )
}

fun main() {
    println("Answer 1: ${solvePuzzle(13, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(13, ::solvePuzzle2)}")
}
