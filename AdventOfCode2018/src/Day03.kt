data class Claim(val id: Int, val distanceFromTopLeft: Coordinate, val size: Coordinate) {
    val coordinates: List<Coordinate>
        get() {
            val coordinates = mutableListOf<Coordinate>()
            for (x in 1..size.x) {
                for (y in 1..size.y) {
                    coordinates.add(distanceFromTopLeft.add(x, y))
                }
            }
            return coordinates.toList()
        }
}

data class Coordinate(val x: Int, val y: Int) {
    constructor(coordinateValues: List<Int>) : this(coordinateValues[0], coordinateValues[1])

    fun add(other: Coordinate): Coordinate {
        return Coordinate(x + other.x, y + other.y)
    }

    fun add(x: Int, y: Int): Coordinate {
        return Coordinate(this.x + x, this.y + y)
    }
}

data class GridEntry(val claims: MutableList<Claim> = mutableListOf())

class ClaimParser {
    companion object {
        val regex = Regex("""^#([\d]+?) @ ([\d]+?),([\d]+?): ([\d]+?)x([\d]+?)$""")

        fun parseClaims(input: List<String>): List<Claim> {
            return input.map { parseClaim(it) }
        }

        private fun parseClaim(input: String): Claim {
            return Claim(parseId(input), parsePosition(input), parseSize(input))
        }

        private fun parseId(input: String): Int {
            return regex.find(input)?.groupValues?.get(1)?.toInt()
                ?: throw Exception("No id found in: $input")
        }

        private fun parsePosition(input: String): Coordinate {
            return Coordinate(regex.find(input)?.groupValues?.subList(2, 4)?.map { it.toInt() }
                ?: throw Exception("No position found in: $input"))
        }

        private fun parseSize(input: String): Coordinate {
            return Coordinate(regex.find(input)?.groupValues?.subList(4, 6)?.map { it.toInt() }
                ?: throw Exception("No size found in: $input"))
        }
    }
}

class Day03part1 : Puzzle<String>(3, 1) {
    override fun solve(input: List<String>): String {
        val claims = ClaimParser.parseClaims(input)
        val grid = mutableMapOf<Coordinate, GridEntry>()
        claims.forEach { claim ->
            claim.coordinates.forEach { coordinate ->
                grid.getOrPut(coordinate) { GridEntry() }.claims.add(claim)
            }
        }
        return grid.filterValues { girdEntry -> girdEntry.claims.size > 1 }
            .count()
            .toString()
    }
}

class Day03part2 : Puzzle<String>(3, 2) {
    override fun solve(input: List<String>): String {
        val claims = ClaimParser.parseClaims(input)
        val grid = mutableMapOf<Coordinate, GridEntry>()
        val cleanClaims = claims.toMutableList()
        claims.forEach { claim ->
            claim.coordinates.forEach { coordinate ->
                val claimsAtCoordinate = grid.getOrPut(coordinate) { GridEntry() }.claims
                claimsAtCoordinate.add(claim)
                if (claimsAtCoordinate.size > 1) {
                    cleanClaims.removeAll(claimsAtCoordinate)
                }
            }
        }
        return cleanClaims.first().id.toString()

    }
}

