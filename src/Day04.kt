typealias Direction = Pair<Int, Int>

fun main() {

    fun xmas(input: List<String>, i: Int, j: Int, d: Direction, target: String, idx: Int = 0): Boolean {
        if (idx == target.length) return true
        if (i !in 0..<input.size || j !in 0..<input.first().length) return false
        if (input[i][j] != target[idx]) return false

        val (ii, jj) = (i + d.first to j + d.second)
        return xmas(input, ii, jj, d, target, idx + 1)
    }

    fun part1(input: List<String>): Int {
        check(input.all { it.length == input.first().length })

        val direction: List<Direction> = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0, 1 to 1, 1 to -1, -1 to 1, -1 to -1)
        var ans = 0

        input.forEachIndexed { i, s ->
            s.forEachIndexed { j, _ ->
                ans += direction.count { d -> xmas(input, i, j, d, "XMAS") }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        check(input.all { it.length == input.first().length })

        val slash: Direction = 1 to -1
        val inverseSlash: Direction = 1 to 1
        val target = listOf("MAS", "SAM")
        var ans = 0

        input.forEachIndexed { i, s ->
            s.forEachIndexed { j, _ ->
                if (target.any { t -> xmas(input, i, j - 1, inverseSlash, t) }
                    && target.any { t -> xmas(input, i, j + 1, slash, t) })
                    ans++
            }
        }
        return ans
    }

    val testcase = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().lines()

    check(part1(testcase) == 18)
    check(part2(testcase) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
