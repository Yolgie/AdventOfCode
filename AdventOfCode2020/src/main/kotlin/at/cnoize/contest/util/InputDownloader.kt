package at.cnoize.contest.util

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.time.LocalDate

val DAY = LocalDate.now().dayOfMonth
val URL = "https://adventofcode.com/2020/day/$DAY/input"

fun main() {
    val request = Request.Builder()
        .addHeader(
            "Cookie",
            "session=53616c7465645f5f6c1722965cee6ffa4046cdca296432703083dd5dc87e7d4610e62d8bafe7da50c515fa459e329423"
        )
        .url(URL)
        .get()
        .build()

    OkHttpClient().newCall(request).execute()
        .use { response ->
            File("src/main/resources/Day${DAY.zeroPad(2)}.input")
                .writeText(response.body?.string() ?: "")
        }

    File("src/main/resources/Day${DAY.zeroPad(2)}.input.test").createNewFile()
}
