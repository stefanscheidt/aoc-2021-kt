package day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import utils.solvePuzzle

sealed interface Move {
    val units: Int
}

data class Forward(override val units: Int) : Move
data class Up(override val units: Int) : Move
data class Down(override val units: Int) : Move

fun String.parseMove(): Move {
    val parts = split(" ")
    return when (parts[0]) {
        "forward" -> Forward(parts[1].toInt())
        "up" -> Up(parts[1].toInt())
        "down" -> Down(parts[1].toInt())
        else -> error("invalid move $this")
    }
}

data class Position(val horizontal: Int, val depth: Int, val aim: Int)

fun applyMove1(pos: Position, move: Move): Position =
    when (move) {
        is Forward -> pos.copy(horizontal = pos.horizontal + move.units)
        is Up -> pos.copy(depth = pos.depth - move.units)
        is Down -> pos.copy(depth = pos.depth + move.units)
    }

fun applyMove2(pos: Position, move: Move): Position =
    when (move) {
        is Forward -> pos.copy(horizontal = pos.horizontal + move.units, depth = pos.depth + pos.aim * move.units)
        is Up -> pos.copy(aim = pos.aim - move.units)
        is Down -> pos.copy(aim = pos.aim + move.units)
    }

fun solvePuzzle1(input: Sequence<String>): Long =
    solve(input, ::applyMove1)

fun solvePuzzle2(input: Sequence<String>): Long =
    solve(input, ::applyMove2)

private fun solve(input: Sequence<String>, f: (Position, Move) -> Position): Long {
    val finalPosition = input.map(String::parseMove).fold(Position(0, 0, 0), f)
    return (finalPosition.horizontal * finalPosition.depth).toLong()
}

class Day02Tests {

    private val sample = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent()

    @Test
    fun `parse move`() {
        "forward 5".parseMove() shouldBe Forward(5)
        "up 3".parseMove() shouldBe Up(3)
        "down 8".parseMove() shouldBe Down(8)
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 150L
    }

    @Test
    fun `solve first puzzle`() {
        solvePuzzle(2, ::solvePuzzle1) shouldBe 1561344L
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 900L
    }

    @Test
    fun `solve second puzzle`() {
        solvePuzzle(2, ::solvePuzzle2) shouldBe 1848454425L
    }
}
