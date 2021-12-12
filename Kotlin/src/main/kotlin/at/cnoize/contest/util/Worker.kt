package at.cnoize.contest.util

import kotlin.system.measureTimeMillis

fun interface Worker {
    fun work(input: Iterable<String>): String

    fun withInputFileAsLines(inputFile: String, options: WorkerOptions? = null) =
        withInputFile(inputFile, String::splitOnNewLine, options)

    fun withInputFileAsSingleString(inputFile: String, options: WorkerOptions? = null) =
        withInputFile(inputFile, ::listOf, options)

    fun withInputFile(
        inputFile: String,
        inputPreprocessing: (String) -> Iterable<String>,
        options: WorkerOptions? = null
    ) {
        val (time, result) = inputFile.asResource { wholeInput ->
            workTimed(inputPreprocessing(wholeInput))
        }
        decorateAndPrint(options, time, result)
    }

    private fun workTimed(input: Iterable<String>): Pair<Long, String> {
        var result: String
        val timeElapsed = measureTimeMillis {
            result = work(input)
        }
        return timeElapsed to result
    }

    private fun decorateAndPrint(
        options: WorkerOptions?,
        time: Long?,
        result: String?
    ) {
        if (options?.title?.isNotEmpty() == true)
            print("[${options.title.padStart(options.titlePadToSize)}]")
        if (time != null)
            print("[${time.toString().padStart(options?.timePadToSize ?: 0, '.')}] ")
        println(result)
    }

    private inline fun <RETURN_TYPE> String.asResource(work: (String) -> RETURN_TYPE): RETURN_TYPE {
        return Thread.currentThread().contextClassLoader
            .getResource(this)
            ?.readText()
            ?.let { work(it) }
            ?: throw IllegalArgumentException("Resource not found: $this")
    }
}

data class WorkerOptions(
    val title: String? = "null",
    val titlePadToSize: Int = 0,
    val timePadToSize: Int = 3
)
