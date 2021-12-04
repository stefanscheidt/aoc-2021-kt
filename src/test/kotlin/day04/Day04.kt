package day04

data class Position(val x: Int, val y: Int)

typealias Card = List<List<Int>>

fun cardOf(vararg rows: List<Int>): Card = listOf(*rows)

fun Card.find(n: Int): Position? {
    forEachIndexed { x, row ->
        val y = row.indexOf(n)
        if (y != -1) return Position(x, y)
    }
    return null
}

data class BingoCard(
    val card: Card,
    internal val marked: Set<Position> = emptySet()
) {

    val solved: Boolean
        get() =
            (card.indices).any { index ->
                marked.count { it.x == index } == card.size ||
                    marked.count { it.y == index } == card.size
            }

    fun mark(n: Int): BingoCard {
        val pos = card.find(n)
        return if (pos == null) this else copy(marked = marked + pos)
    }

    fun score(n: Int): Int {
        val sum = card
            .flatMapIndexed { x, row ->
                row.mapIndexed { y, v -> Position(x, y) to v }
            }
            .filter { (pos, _) -> pos !in marked }
            .sumOf { (_, v) -> v }
        return sum * n
    }
}

fun bingoCardOf(vararg rows: List<Int>): BingoCard =
    BingoCard(listOf(*rows))

fun bingoCardOf(strings: List<String>): BingoCard {
    val card = strings
        .filter(String::isNotBlank)
        .map { row ->
            row.trim()
                .split("""\s+""".toRegex())
                .map(String::toInt)
        }
    return BingoCard(card)
}

fun solvePuzzle1(input: Sequence<String>): Long {
    val lines = input.toList()
    val ns = lines.first().split(",").map(String::toInt)
    var cards = lines.drop(1)
        .chunked(6)
        .map(::bingoCardOf)

    ns.forEach { n ->
        cards = cards.map { it.mark(n) }
        val winner = cards.find(BingoCard::solved)
        if (winner != null) return winner.score(n).toLong()
    }

    return 0L
}
