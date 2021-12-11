package day10

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test

class Day10Tests {

    @Test
    internal fun `parse a valid line`() {
        parseLine("") shouldBe Success
        parseLine("[<>({}){}[([])<>]]") shouldBe Success
    }

    @Test
    internal fun `parse a corrupted line`() {
        parseLine(")") shouldBe Corrupted(')')
        parseLine("{([(<{}[<>[]}>{[]{[(<()>") shouldBe Corrupted('}')
    }

    @Test
    internal fun `parse an incomplete line`() {
        parseLine("(").shouldBeTypeOf<Incomplete>()
        parseLine("[({(<(())[]>[[{[]{<()<>>").shouldBeTypeOf<Incomplete>()
    }

    @Test
    fun `solve first puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle1(input) shouldBe 26397L
    }

    @Test
    fun `solve second puzzle with sample`() {
        val input = sample.lineSequence()

        solvePuzzle2(input) shouldBe 288957L
    }

    private val sample = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent()
}
