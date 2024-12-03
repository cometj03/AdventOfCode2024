fun main() {
    fun isSafe(row: List<Int>): Boolean {
        val isUp = row.let {
            (1 until row.size).forEach { i ->
                if (row[i] - row[i - 1] !in (1..3)) return@let false
            }
            true
        }
        val isDown = row.let {
            (1 until row.size).forEach { i ->
                if (row[i] - row[i - 1] !in (-3..-1)) return@let false
            }
            true
        }
        return isUp || isDown
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ").map(String::toInt) }
            .count { row -> isSafe(row) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ").map(String::toInt) }
            .count { row ->
                if (isSafe(row)) return@count true
                row.indices.forEach { i ->
                    val sublist = row.subList(0, i) + row.subList(i + 1, row.size)
                    if (isSafe(sublist)) return@count true
                }
                false
            }
    }

    val testcase = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()

    check(part1(testcase) == 2)
    check(part2(testcase) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}