package at.cnoize.contest.adventOfCode2021.day14

import at.cnoize.contest.util.*
import java.math.BigInteger

private const val YEAR = 2021
private const val DAY = "14"

private const val INPUT_FILE_TEST = "adventOfCode$YEAR/Day$DAY.input.test"
private const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input"

@Suppress("DuplicatedCode")
fun main() {
    val optionsForTestRun = WorkerOptions(title = "Test")
    val optionsForFullRun = WorkerOptions(title = "Full")

    println("Advent of Code $YEAR $DAY")
    println("Part 1:")
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE, optionsForFullRun)
    println("Part 2:")
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE_TEST, optionsForTestRun)
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE, optionsForFullRun)
}

private val workerPuzzle1 = Worker { input: Iterable<String> ->
    val (initialTemplate, rules) = input.parseToTemplateAndInsertionRules()
    val applyRules = { template: String -> template.applyRules(rules) }

    generateSequence(initialTemplate, applyRules::invoke)
        .take(11)
        .last()
        .toList()
        .eachCount()
        .minAndMaxValue()
        .swap().subtract()
        .toString()
}

private fun Iterable<String>.parseToTemplateAndInsertionRules(): Pair<String, Map<String, String>> {
    val (template, rulesString) = this.only().splitOnEmptyLine()
    val rules = rulesString
        .splitOnNewLine()
        .map { it.split(" -> ") }
        .associate(List<String>::toPair)
        .mapValues { (key, value) -> "${key.first()}$value${key.last()}" }
    return template to rules
}

private fun String.applyRules(rules: Map<String, String>): String {
    return this.first() + this.zipWithNext()
        .map { it.joinToString("") }
        .map { rules.getOrDefault(it, it) }
        .map { it.drop(1) }
        .joinToString("")
}

private val workerPuzzle2 = Worker { input: Iterable<String> ->
    val (initialCount, rules) = input.parseToInitialCountAndInsertionRulesAsTuples()
    val applyRules = { count: Count -> count.applyRules(rules) }

    val counts = mutableMapOf<Char, BigInteger>()
    counts[input.first().first()] = BigInteger.ONE // because only second char of every tuple is counted later

    generateSequence(initialCount, applyRules::invoke)
        .take(41)
        .last()
        .forEach { (tuple, count) ->
            counts[tuple.second] = counts.getOrDefault(tuple.second, BigInteger.ZERO) + count
        }

    counts.minAndMaxValue().swap().subtract().toString()
}

private typealias Tuple = Pair<Char, Char>
private typealias Rules = Map<Tuple, List<Tuple>>
private typealias Count = Map<Tuple, BigInteger>

private fun Iterable<String>.parseToInitialCountAndInsertionRulesAsTuples(): Pair<Count, Rules> {
    val (templateString, rulesString) = this.only().splitOnEmptyLine()
    val initialCount = templateString
        .zipWithNext()
        .eachCount()
        .mapValues { (_, value) -> value.toBigInteger() }
    val rules = rulesString
        .splitOnNewLine()
        .map { it.split(" -> ") }
        .associate(List<String>::toPair)
        .mapKeys { (key, _) -> key.toList().toPair() }
        .mapValues { (_, value) -> value.first() }
        .mapValues { (key, value) -> listOf(key.first to value, value to key.second) }
    return initialCount to rules
}

private fun Count.applyRules(rules: Rules): Count {
    val counts = mutableMapOf<Tuple, BigInteger>()

    this.forEach { (oldTuple, oldCount) ->
        rules.getOrDefault(oldTuple, listOf(oldTuple))
            .forEach { tuple ->
                counts[tuple] = counts.getOrDefault(tuple, BigInteger.ZERO) + oldCount
            }
    }

    return counts
}

