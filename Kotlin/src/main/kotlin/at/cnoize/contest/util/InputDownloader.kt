package at.cnoize.contest.util

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.time.LocalDate

val YEAR = 2021
val DAY = LocalDate.now().dayOfMonth
val URL = "https://adventofcode.com/$YEAR/day/$DAY/input"

fun main() {
    val request = Request.Builder()
        .addHeader(
            "Cookie",
            "session=***REMOVED***"
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
