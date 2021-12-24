package day16

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16Tests {

    @Test
    internal fun `parse to binary input`() {
        parseBinaryInput("D2FE28").asString() shouldBe "110100101111111000101000"
    }

    @Test
    internal fun `next int from binary input`() {
        val binaryInput = "000000000011011".toList().iterator()

        binaryInput.nextInt(15) shouldBe 27
    }

    @Test
    internal fun `next literal from binary input`() {
        val binaryInput = "101111111000101".toList().iterator()

        binaryInput.nextLiteral(6) shouldBe Literal(6, 2021)
    }

    @Test
    internal fun `next operator with length type 0 from binary input`() {
        // ............... ILLLLLLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBBBBBB
        val binaryInput = "00000000000110111101000101001010010001001000000000".toList().iterator()

        binaryInput.nextOperator(1, 6) shouldBe Operator(1, 6, listOf(Literal(6, 10), Literal(2, 20)))
    }

    @Test
    internal fun `next operator with length type 1 from binary input`() {
        // ............... ILLLLLLLLLLLAAAAAAAAAAABBBBBBBBBBBCCCCCCCCCCC
        val binaryInput = "10000000001101010000001100100000100011000001100000".toList().iterator()

        binaryInput.nextOperator(7, 3) shouldBe Operator(7, 3, listOf(Literal(2, 1), Literal(4, 2), Literal(1, 3)))
    }

    @Test
    fun `solve first puzzle with sample`() {
        solvePuzzle1("8A004A801A8002F478".lineSequence()) shouldBe 16
        solvePuzzle1("620080001611562C8802118E34".lineSequence()) shouldBe 12
        solvePuzzle1("C0015000016115A2E0802F182340".lineSequence()) shouldBe 23
        solvePuzzle1("A0016C880162017C3686B18A3D4780".lineSequence()) shouldBe 31
    }

    @Test
    fun `solve second puzzle with sample`() {
        solvePuzzle2("C200B40A82".lineSequence()) shouldBe 3L
        solvePuzzle2("04005AC33890".lineSequence()) shouldBe 54L
        solvePuzzle2("880086C3E88112".lineSequence()) shouldBe 7L
    }
}
