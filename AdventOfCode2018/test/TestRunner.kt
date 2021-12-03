import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class Day04TestRunner<T>(test: TestInput<T>) : TestRunner<T>(test) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<TestInput<*>> {
            val testInput = "[1518-11-01 00:00] Guard #10 begins shift\n" +
                    "[1518-11-01 00:05] falls asleep\n" +
                    "[1518-11-02 00:40] falls asleep\n" +
                    "[1518-11-03 00:05] Guard #10 begins shift\n" +
                    "[1518-11-03 00:24] falls asleep\n" +
                    "[1518-11-03 00:29] wakes up\n" +
                    "[1518-11-01 00:25] wakes up\n" +
                    "[1518-11-02 00:50] wakes up\n" +
                    "[1518-11-01 00:30] falls asleep\n" +
                    "[1518-11-01 00:55] wakes up\n" +
                    "[1518-11-01 23:58] Guard #99 begins shift\n" +
                    "[1518-11-04 00:02] Guard #99 begins shift\n" +
                    "[1518-11-04 00:36] falls asleep\n" +
                    "[1518-11-04 00:46] wakes up\n" +
                    "[1518-11-05 00:03] Guard #99 begins shift\n" +
                    "[1518-11-05 00:45] falls asleep\n" +
                    "[1518-11-05 00:55] wakes up"
            return listOf(
                TestInput(testInput, "240", Day04part1(), delimiter = "\n"),
                TestInput(testInput, "4455", Day04part2(), delimiter = "\n")
            )
        }
    }
}

@RunWith(Parameterized::class)
class Day03TestRunner<T>(test: TestInput<T>) : TestRunner<T>(test) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<TestInput<*>> {
            return listOf(
                TestInput("#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2", "4", Day03part1(), delimiter = "\n"),
                TestInput("#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2", "3", Day03part2(), delimiter = "\n")
            )
        }
    }
}

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

class TestInput<T>(input: String, expectedOutput: T, val puzzle: Puzzle<T>, val delimiter: String = ",") {
    val expectedOutput = listOf(expectedOutput.toString())
    val input = perpareInput(input)

    fun perpareInput(input: String): List<String> {
        return input.split(delimiter).map { it.trim() }
    }
}

abstract class TestRunner<T>(val test: TestInput<T>) {
    @Test
    fun testLevel() {
        Assert.assertEquals(test.expectedOutput, test.puzzle.solveInRunner(test.input))
    }
}

