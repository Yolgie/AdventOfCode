package at.cnoize.contest.util

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.time.LocalDate

val YEAR = 2021
val DAY = LocalDate.now().dayOfMonth
val URL = "https://adventofcode.com/2021/day/$DAY/input"

fun main() {
    val request = Request.Builder()
        .addHeader(
            "Cookie",
            "session=53616c7465645f5fc0bb864746c0cca9800f4a6b299e668c50c80aee745c5cce0bf0e49316dfaaf3d3fbbfd5ec8aa2ec"
        )
        .url(URL)
        .get()
        .build()

    OkHttpClient().newCall(request).execute()
        .use { response ->
            File("src/main/resources/adventOfCode$YEAR/Day${DAY.zeroPad(2)}.input")
                .writeText(response.body?.string() ?: "")
        }

    File("src/main/resources/adventOfCode$YEAR/Day${DAY.zeroPad(2)}.input.test").createNewFile()
}
