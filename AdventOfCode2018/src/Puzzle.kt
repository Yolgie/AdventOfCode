
@Suppress("UNCHECKED_CAST") // if T is String (default) this is a cast from String -> String, other cases should override this
abstract class Puzzle<T>(day: Int, val part: Int, val parse: (String) -> T = { s -> s as T }) {
    val day = day.toString().padStart(2, '0')

    abstract fun solve(input: List<T>): T

    open fun solveInRunner(input: List<String>): List<String> {
        return listOf(solve(input.map { parse(it) }).toString())
    }
}

abstract class MultilineSolutionPuzzle<T>(day: Int, part: Int, parse: (String) -> T) : Puzzle<T>(day, part, parse) {
    override fun solve(input: List<T>): T {
        throw Exception("Not applicable method signature")
    }

    abstract fun solveMultiline(input: List<T>): List<T>

    override fun solveInRunner(input: List<String>): List<String> {
        return solveMultiline(input.map { parse(it) }).map { it.toString() }
    }
}

abstract class IntPuzzle(day: Int, part: Int) : Puzzle<Int>(day, part, String::toInt)