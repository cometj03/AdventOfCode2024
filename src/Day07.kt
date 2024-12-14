import kotlin.math.pow

fun main() {
    fun LongArray.bitsum(bit: Int): Long {
        var sum = get(0)
        for (i in 1 ..< this.size) {
            if (bit and (1 shl (i - 1)) == 0) sum += get(i)
            else sum *= get(i)
        }
        return sum
    }

    fun part1(input: List<String>): Long {
        var ans = 0L
        for (str in input.map { it.split(" ") }) {
            val test = str[0].dropLast(1).toLong()
            val num = str.drop(1).map(String::toLong).toLongArray()

            for (bit in 0 ..< (1 shl (num.size - 1))) {
                if (test == num.bitsum(bit)) {
                    ans += test
                    break
                }
            }
        }
        return ans
    }

    fun LongArray.sumMask(mask: IntArray): Long {
        var sum = get(0)
        for (i in 1 ..< this.size) {
            sum = when (mask[i - 1]) {
                0 -> sum + get(i)
                1 -> sum * get(i)
                2 -> "${sum}${get(i)}".toLong()
                else -> sum
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var ans = 0L
        for (str in input.map { it.split(" ") }) {
            val test = str[0].dropLast(1).toLong()
            val num = str.drop(1).map(String::toLong).toLongArray()
            val mask = IntArray(num.size - 1)

            for (bit in 0L ..< (3.0.pow(num.size - 1).toLong())) {
                mask.fill(0)
                var _bit = bit
                var i = 0
                while (_bit != 0L) {
                    mask[i] = (_bit % 3).toInt()
                    i++
                    _bit /= 3L
                }
                if (test == num.sumMask(mask)) {
                    ans += test
                    break
                }
            }
        }
        return ans
    }

    val testcase = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().lines()

    check(part1(testcase).println() == 3749L)
    check(part2(testcase).println() == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}