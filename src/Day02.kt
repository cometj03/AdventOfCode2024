fun main() {
    fun isSafe(row: List<Int>): Boolean {
        val isUp = row.zipWithNext().all { (a, b) -> b - a in (1..3) }
        val isDown = row.zipWithNext().all { (a, b) -> a - b in (1..3) }
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
                isSafe(row) || row.indices.any { i ->
                    val sublist = row.subList(0, i) + row.subList(i + 1, row.size)
                    isSafe(sublist)
                }
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