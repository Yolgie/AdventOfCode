import java.io.File
import kotlin.system.measureTimeMillis

val INPUT_DIR = "input/"
val OUTPUT_DIR = "output/"

fun main(args: Array<String>) {
    val timeElapsed = measureTimeMillis {
        run(Day04part2())
    }
    println("\n$timeElapsed")
}

fun <T> run(puzzle: Puzzle<T>) {
    val inputFile: File = getInputFile(puzzle.day)
    val input: List<String> = inputFile.readLines()
    val output: List<String> = puzzle.solveInRunner(input)
    val outputFile: File = getOutputFile(puzzle.day, puzzle.part)
    val outputAsText = output.joinToString("/n")
    outputFile.writeText(outputAsText)
    System.out.print(outputAsText)
}

private fun getInputFile(day: String): File {
    return File("$INPUT_DIR/day$day.input")
}

private fun getOutputFile(day: String, part: Int, count: Int = 1): File {
    val outputFile = File("$OUTPUT_DIR/day$day.$part.$count.output")
    return if (outputFile.exists()) {
        getOutputFile(day, part, count + 1)
    } else {
        outputFile
    }
}
