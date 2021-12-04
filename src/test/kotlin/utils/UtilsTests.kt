package utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UtilsTests {

    @Test
    internal fun `transpose a matrix`() {
        val matrix = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
        )
        val transposed = listOf(
            listOf(1, 4),
            listOf(2, 5),
            listOf(3, 6),
        )

        matrix.transposed() shouldBe transposed
    }
}
