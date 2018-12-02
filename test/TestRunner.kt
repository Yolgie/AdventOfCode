import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class Day02TestRunner<T>(test: TestInput<T>) : TestRunner<T>(test) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<TestInput<*>> {
            return listOf(
                TestInput("abcdef, bababc, abbcde, abcccd, aabcdd, abcdee, ababab", "12", Day02part1()),
                TestInput("abcde, fghij, klmno, pqrst, fguij, axcye, wvxyz", "fgij", Day02part2())
            )
        }
    }
}

@RunWith(Parameterized::class)
class Day01TestRunner<T>(test: TestInput<T>) : TestRunner<T>(test) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<TestInput<*>> {
            return listOf(
                TestInput("+1, +1, +1", 3, Day01part1()),
                TestInput("+1, +1, -2", 0, Day01part1()),
                TestInput("-1, -2, -3", -6, Day01part1()),
                TestInput("+1, -1", 0, Day01part2()),
                TestInput("+3, +3, +4, -2, -4", 10, Day01part2()),
                TestInput("-6, +3, +8, +5, -6", 5, Day01part2()),
                TestInput("+7, +7, -2, -7, -4", 14, Day01part2())

            )
        }
    }
}

class TestInput<T>(input: String, expectedOutput: T, val puzzle: Puzzle<T>) {
    val expectedOutput = listOf(expectedOutput.toString())
    val input = perpareInput(input)

    fun perpareInput(input: String): List<String> {
        return input.split(",").map { it.trim() }
    }
}

abstract class TestRunner<T>(val test: TestInput<T>) {
    @Test
    fun testLevel() {
        Assert.assertEquals(test.expectedOutput, test.puzzle.solveInRunner(test.input))
    }
}

