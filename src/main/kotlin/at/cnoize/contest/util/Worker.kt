package at.cnoize.contest.util

fun interface Worker {
    fun work(input: Iterable<String>): String

    fun withInputFile(inputFile: String, title: String? = null) {
        print(title)
        println(
            inputFile.asResource { wholeInput ->
                work(
                    wholeInput
                        .split('\n')
                        .map(String::trim)
                        .filterNot(String::isBlank)
                )
            }
        )
    }

    fun withInputFileAsSingleString(inputFile: String, title: String? = null) {
        print(title)
        println(
            inputFile.asResource { wholeInput ->
                work(listOf(wholeInput))
            }
        )
    }
}
