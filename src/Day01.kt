import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val left = input.map { it.split("\\s+".toRegex())[0].toInt() }.sorted()
        val right = input.map { it.split("\\s+".toRegex())[1].toInt() }.sorted()
        return left.zip(right).sumOf { (a, b) -> abs(a - b) }
    }

    fun part2(input: List<String>): Int {
        val count = IntArray(101010) { 0 }
        val left = input.map {
            val (l, r) = it.split("\\s+".toRegex()).map(String::toInt)
            count[r]++
            l
        }
        return left.sumOf { it * count[it] }
    }

    val testcase = listOf(
        "3  4",
        "4  3",
        "2  5",
        "1  3",
        "3  9",
        "3  3"
    )

    check(part1(testcase) == 11)
    check(part2(testcase) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
