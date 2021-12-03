package at.cnoize.contest.adventOfCode2020.day04

import at.cnoize.contest.util.Worker

const val YEAR = 2020
const val DAY = "04"

const val INPUT_FILE = "adventOfCode$YEAR/Day$DAY.input.test"
//const val INPUT_FILE ="adventOfCode$YEAR/Day$DAY.input"

fun main() {
    workerPuzzle1.withInputFileAsSingleString(INPUT_FILE)
    workerPuzzle2.withInputFileAsSingleString(INPUT_FILE)
}

val workerPuzzle1 = Worker { input ->
    input.first()
        .split("\n\n")
        .map { it.replace("\n", " ") }
        .map(String::toPassport)
        .count(Passport::isThere)
        .toString()
}

val workerPuzzle2 = Worker { input ->
    input.first()
        .split("\n\n")
        .map { it.replace("\n", " ") }
        .map(String::toPassport)
        .count(Passport::isValid)
        .toString()
}

fun String.toPassport(): Passport {
    return this.trim().split(" ")
        .fold(Passport()) { passport, element ->
            val (field, value) = element.split(":")
            PassportField.fromAbbreviation(field)?.toPassport?.invoke(passport, value)
                ?: throw IllegalStateException("Could not map field '$field' to passport")
        }
}

data class Passport(
    val birthYear: BirthYear? = null,
    val issueYear: IssueYear? = null,
    val expirationYear: ExpirationYear? = null,
    val height: Height? = null,
    val hairColor: HairColor? = null,
    val eyeColor: EyeColor? = null,
    val passportId: PassportId? = null,
    val countryId: CountryId? = null,
) {
    private val fields =
        listOf(birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId)
    val isValid = fields.all { it?.isValid ?: false }
    val isThere = fields.none { it == null }
}

interface Validated {
    val isValid: Boolean
}

open class Year(year: Int, validRange: IntRange) : Validated {
    override val isValid = year in validRange
}

data class BirthYear(val year: Int) : Year(year, validRange = 1920..2002), Validated
data class IssueYear(val year: Int) : Year(year, validRange = 2010..2020), Validated
data class ExpirationYear(val year: Int) : Year(year, validRange = 2020..2030), Validated
data class Height(val height: String) : Validated {
    override val isValid: Boolean
        get() {
            val unit = height.takeLast(2)
            val height = height.dropLast(2).toInt()
            return when (unit) {
                "cm" -> height in 150..193
                "in" -> height in 59..76
                else -> false
            }
        }
}

data class HairColor(val color: String) : Validated {
    override val isValid = validRegex.matches(color)

    companion object {
        private val validRegex = """^#[a-f0-9]{6}$""".toRegex()
    }
}

data class EyeColor(val color: String) : Validated {
    override val isValid = color in validEyeColors

    companion object {
        private val validEyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }
}

data class PassportId(val id: String) : Validated {
    override val isValid = validRegex.matches(id)

    companion object {
        val validRegex = """^[\d]{9}$""".toRegex()
    }
}

data class CountryId(val id: String) : Validated {
    override val isValid = true
}

enum class PassportField(
    val abbreviation: String,
    val toPassport: (passport: Passport, value: String?) -> Passport,
) {
    BIRTHYEAR("byr", { passport, value -> passport.copy(birthYear = value?.toInt()?.let(::BirthYear)) }),
    ISSUEYEAR("iyr", { passport, value -> passport.copy(issueYear = value?.toInt()?.let(::IssueYear)) }),
    EXPIRATIONYEAR("eyr", { passport, value -> passport.copy(expirationYear = value?.toInt()?.let(::ExpirationYear)) }),
    HEIGHT("hgt", { passport, value -> passport.copy(height = value?.let(::Height)) }),
    HAIRCOLOR("hcl", { passport, value -> passport.copy(hairColor = value?.let(::HairColor)) }),
    EYECOLOR("ecl", { passport, value -> passport.copy(eyeColor = value?.let(::EyeColor)) }),
    PASSPORTID("pid", { passport, value -> passport.copy(passportId = value?.let(::PassportId)) }),
    COUNTRYID("cid", { passport, value -> passport.copy(countryId = value?.let(::CountryId)) }),
    ;

    companion object {
        private val abbreviationMap = values().associateBy(PassportField::abbreviation)

        fun fromAbbreviation(appreviation: String) = abbreviationMap[appreviation]
    }
}
