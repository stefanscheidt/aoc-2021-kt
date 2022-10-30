package day16

import utils.solvePuzzle

// packets

sealed interface Packet {
    val version: Int
    val value: Long
}

data class Literal(
    override val version: Int,
    override val value: Long,
) : Packet

data class Operator(
    override val version: Int,
    val type: Int,
    val packets: List<Packet>,
) : Packet {
    override val value: Long
        get() = when (type) {
            0 -> packets.sumOf(Packet::value)
            1 -> packets.fold(1) { acc, packet -> acc * packet.value }
            2 -> packets.minOf(Packet::value)
            3 -> packets.maxOf(Packet::value)
            5 -> if (packets[0].value > packets[1].value) 1 else 0
            6 -> if (packets[0].value < packets[1].value) 1 else 0
            7 -> if (packets[0].value == packets[1].value) 1 else 0
            else -> error("invalid operator type $type")
        }
}

// binary input

typealias BinaryInput = Iterator<Char>

fun BinaryInput.asString(): String =
    asSequence().joinToString("")

fun BinaryInput.nextString(n: Int): String =
    (1..n).map { next() }.joinToString("")

fun BinaryInput.nextInt(n: Int): Int =
    nextString(n).toInt(2)

fun BinaryInput.nextPacket(): Packet {
    val version = nextInt(3)
    val type = nextInt(3)
    return if (type == 4) nextLiteral(version) else nextOperator(version, type)
}

fun BinaryInput.nextLiteral(version: Int): Literal {
    val bits = buildList {
        do {
            val word = nextString(5)
            add(word.drop(1))
        } while (word.startsWith('1'))
    }.joinToString("")
    return Literal(version, bits.toLong(2))
}

fun BinaryInput.nextOperator(version: Int, type: Int): Operator =
    when (nextInt(1)) {
        0 -> {
            val packets = mutableListOf<Packet>()
            val packetsInput = nextString(nextInt(15)).iterator()
            while (packetsInput.hasNext()) packets.add(packetsInput.nextPacket())
            Operator(version, type, packets)
        }
        1 -> {
            val packets = (1..nextInt(11)).map { nextPacket() }
            Operator(version, type, packets)
        }
        else -> error("invalid length type")
    }

fun parseBinaryInput(input: String): BinaryInput =
    input.map { it.digitToInt(16).toString(2).padStart(4, '0') }
        .flatMap { it.toList() }
        .iterator()

fun solvePuzzle1(input: Sequence<String>): Int {
    fun sumOfVersions(packet: Packet): Int =
        when (packet) {
            is Literal -> packet.version
            is Operator -> packet.version + packet.packets.sumOf(::sumOfVersions)
        }

    val packet = parseBinaryInput(input.first()).nextPacket()
    return sumOfVersions(packet)
}

fun solvePuzzle2(input: Sequence<String>): Long =
    parseBinaryInput(input.first()).nextPacket().value

fun main() {
    println("Answer 1: ${solvePuzzle(16, ::solvePuzzle1)}")
    println("Answer 2: ${solvePuzzle(16, ::solvePuzzle2)}")
}
