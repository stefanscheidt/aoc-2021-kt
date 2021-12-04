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

private fun numbers(input: List<String>): List<Int> =
    input.first().split(",").map(String::toInt)

private fun cards(input: List<String>): List<BingoCard> =
    input.drop(1).chunked(6).map(::bingoCardOf)

fun solvePuzzle1(input: Sequence<String>): Long {
    val inputList = input.toList()
    val ns = numbers(inputList)
    var cards = cards(inputList)

    ns.forEach { n ->
        cards = cards.map { it.mark(n) }
        val winner = cards.find(BingoCard::solved)
        if (winner != null) return winner.score(n).toLong()
    }

    return 0L
}

fun solvePuzzle2(input: Sequence<String>): Long {
    val inputList = input.toList()
    val ns = numbers(inputList)
    var cards = cards(inputList)

    val winners = mutableListOf<Pair<BingoCard, Int>>()
    ns.forEach { n ->
        val winnersAndLosers = cards.map { it.mark(n) }.partition(BingoCard::solved)
        winners.addAll(winnersAndLosers.first.map { it to n })
        cards = winnersAndLosers.second
    }

    val lastWinner = winners.last()
    return lastWinner.first.score(lastWinner.second).toLong()
}
