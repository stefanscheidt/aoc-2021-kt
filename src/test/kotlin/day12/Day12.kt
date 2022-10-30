package day12

import utils.solvePuzzle

typealias Cave = String

val Cave.isBig: Boolean
    get() = all { char -> char.isUpperCase() }

// associates a cave with all its neighbours
typealias CaveMap = Map<Cave, Set<Cave>>

typealias Path = List<Cave>

fun CaveMap.traverse(path: Path = listOf("start"), canVisit: (Cave, Path) -> Boolean): Set<Path> =
    if (path.last() == "end") {
        setOf(path)
    } else {
        getValue(path.last())
            .filter { cave -> canVisit(cave, path) }
            .flatMap { cave -> traverse(path + cave, canVisit) }
            .toSet()
    }

fun parseCaveMap(input: List<String>): CaveMap =
    input.map { it.split("-") }
        .flatMap {
            val first = it.first()
            val last = it.last()
            listOf(first to last, last to first)
        }.groupBy({ it.first }, { it.second })
        .mapValues { (_, caves) -> caves.toSet() }

fun solvePuzzle1(input: Sequence<String>): Int =
    parseCaveMap(input.toList())
        .traverse(canVisit = { cave, path -> cave.isBig || cave !in path })
        .size

fun solvePuzzle2(input: Sequence<String>): Int {
    fun noSmallCaveVisitedTwice(path: Path /* = kotlin.collections.List<day12.Cave /* = kotlin.String */> */): Boolean =
        path.filterNot { it.isBig }.groupBy { it }.none { (_, visits) -> visits.size == 2 }

    fun canVisit(cave: Cave, path: Path): Boolean =
        when {
            cave == "start" -> false
            cave.isBig -> true
            cave !in path -> true
            else -> noSmallCaveVisitedTwice(path)
        }

    return parseCaveMap(input.toList())
        .traverse(canVisit = ::canVisit)
        .size
}

fun main() {
    println("Answer 1: ${solvePuzzle(12, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(12, ::solvePuzzle2)}")
}
