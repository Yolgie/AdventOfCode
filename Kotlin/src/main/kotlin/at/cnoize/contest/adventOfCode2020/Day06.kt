package at.cnoize.contest.adventOfCode2020.day06

import at.cnoize.contest.util.Worker
import at.cnoize.contest.util.WorkerOptions
import at.cnoize.contest.util.splitOnEmptyLine
import at.cnoize.contest.util.splitOnNewLine

private const val YEAR = 2020
private const val DAY = "06"

private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//private const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 1: \n"))
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE, WorkerOptions(title = "Answer Puzzle 2: \n"))
}

private val workerPuzzle1 = Worker { input ->
    input.first()
        .splitOnEmptyLine()
        .map(String::parseGroup)
        .sumOf { group -> group.allAnswers.count }
        .toString()
}

private val workerPuzzle2 = Worker { input ->
    input.first()
        .splitOnEmptyLine()
        .map(String::parseGroup)
        .sumOf { group -> group.commonAnswers.count }
        .toString()
}

private data class Group(val answers: Collection<Answer>) {
    val allAnswers = answers.reduce(Answer::collect)
    val commonAnswers = answers.reduce(Answer::coalesce)

}

private fun String.parseGroup() = Group(this.splitOnNewLine().map(String::parseCount))

private data class Answer(val answer: Set<Char>) {
    val count = answer.size

    fun collect(other: Answer) = Answer(answer + other.answer)
    fun coalesce(other: Answer) = Answer(answer.filter { it in other.answer }.toSet())
}

private fun String.parseCount() = Answer(this.toSet())
