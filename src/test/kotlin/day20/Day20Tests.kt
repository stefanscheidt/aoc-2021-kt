package day20

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20Tests {

    private val alg = parseAlgorithm(
        """
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
        #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
        .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
        .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
        .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
        ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
        ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
        """.trimIndent(),
    )

    @Test
    fun `parse algorithm`() {
        parseAlgorithm("#.##..") shouldBe listOf(1, 0, 1, 1, 0, 0)

        parseAlgorithm(
            """
            .#.
            ##.
            ..#
            """.trimIndent(),
        ) shouldBe listOf(0, 1, 0, 1, 1, 0, 0, 0, 1)
    }

    @Test
    fun `convert bit array to int`() {
        listOf(0, 0, 0, 1, 0, 0, 0, 1, 0).toInt() shouldBe 34
    }

    @Test
    fun `enhance sample image once`() {
        val image = parseImage(
            """
            #..#.
            #....
            ##..#
            ..#..
            ..###
            """.trimIndent().lines(),
        )

        renderImage(image.enhanceOnce(alg)) shouldBe """
            .##.##.
            #..#.#.
            ##.#..#
            ####..#
            .#..##.
            ..##..#
            ...#.#.
        """.trimIndent()
    }

    @Test
    fun `enhance sample image twice`() {
        val image = parseImage(
            """
            #..#.
            #....
            ##..#
            ..#..
            ..###
            """.trimIndent().lines(),
        )

        image.enhance(alg, 2).sumOf { it.sum() } shouldBe 35
    }
}

private fun renderImage(image: Image): String =
    image.joinToString(separator = "\n") { row ->
        row.map { if (it == 1) '#' else '.' }.joinToString(separator = "")
    }
