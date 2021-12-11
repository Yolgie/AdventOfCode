import java.io.File
import kotlin.system.measureTimeMillis

val INPUT_DIR = "adventOfCode2018/"
val OUTPUT_DIR = "out/"

fun main(args: Array<String>) {
    val timeElapsed = measureTimeMillis {
        run(Day04part2())
    }
    println("\n$timeElapsed")
}

fun <T> run(puzzle: Puzzle<T>) {
    val input: List<String> = getInput(puzzle.day)
    val output: List<String> = puzzle.solveInRunner(input)
    val outputFile: File = getOutputFile(puzzle.day, puzzle.part)
    val outputAsText = output.joinToString("/n")
    outputFile.writeText(outputAsText)
    System.out.print(outputAsText)
}

private fun getInput(day: String): List<String> {
    val url = "$INPUT_DIR/day$day.input"
    return Thread.currentThread().contextClassLoader
        .getResource(url)
        ?.readText()
        ?.split('\n')
        ?.map(String::trim)
        ?.filterNot(String::isBlank)
        ?: throw IllegalArgumentException("Resource not found: $url")
}

private fun getOutputFile(day: String, part: Int, count: Int = 1): File {
    val outputFile = File("$OUTPUT_DIR/day$day.$part.$count.output")
    return if (outputFile.exists()) {
        getOutputFile(day, part, count + 1)
    } else {
        outputFile
    }
}
