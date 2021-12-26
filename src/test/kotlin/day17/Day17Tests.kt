package day17

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17Tests {

    @Test
    internal fun `parse target area`() {
        val area = parseTargetArea("target area: x=20..30, y=-10..-5")

        area shouldBe Area((20..30), (-10..-5))
    }

    @Test
    internal fun `point in area`() {
        val area = parseTargetArea("target area: x=20..30, y=-10..-5")

        (P(0, 0) in area) shouldBe false
        (P(20, -10) in area) shouldBe true
    }

    @Test
    fun `solve first puzzle with sample`() {
        solvePuzzle1("target area: x=20..30, y=-10..-5".lineSequence()) shouldBe 45
    }

    @Test
    fun `solve second puzzle with sample`() {
        solvePuzzle2("target area: x=20..30, y=-10..-5".lineSequence()) shouldBe 112
    }
}
