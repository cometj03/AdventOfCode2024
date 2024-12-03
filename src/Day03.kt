import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun part1(input: String): Int {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()

        return regex.findAll(input)     // Sequence<MatchResult>
            .sumOf { matchResult ->
                val (a: Int, b: Int) = matchResult
                    .groupValues        // groupValues example: ["mul(1,2)", "1", "2"]
                    .takeLast(2)     // take last two String
                    .map(String::toInt)
                a * b
            }
    }

    fun part2(input: String): Int {
        val regex = """(mul\((\d+),(\d+)\)|don't\(\)|do\(\))""".toRegex()
        var canDo = true

        return regex.findAll(input)
            .sumOf { matchResult ->
                when (matchResult.value) {
                    "do()" -> {
                        canDo = true
                        return@sumOf 0
                    }
                    "don't()" -> {
                        canDo = false
                        return@sumOf 0
                    }
                }
                if (!canDo)
                    return@sumOf 0

                val (a: Int, b: Int) = matchResult
                    .groupValues
                    .takeLast(2)
                    .map(String::toInt)
                a * b
            }
    }

    val testcase1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val testcase2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    check(part1(testcase1) == 161)
    check(part2(testcase2) == 48)

    val input1 = Path("src/Day03-1.txt").readText().trim()
    val input2 = Path("src/Day03-2.txt").readText().trim()
    part1(input1).println()
    part2(input2).println()
}