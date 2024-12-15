fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        val n = map.size
        val m = map[0].size
        check(map.all { it.size == m })
        val charMap = HashMap<Char, ArrayList<Pair<Int, Int>>>()

        for (i in 0..<n) for (j in 0..<m) {
            val c = input[i][j]
            if (c == '.') continue
            charMap.getOrPut(c) { ArrayList() }.add(Pair(i, j))
        }

        var ans = 0
        for ((_, arr) in charMap) {
            for (a in arr.indices) for (b in (a + 1)..<arr.size) {
                val (ai, aj) = arr[a]
                val (bi, bj) = arr[b]

                val ni1 = 2 * bi - ai
                val nj1 = 2 * bj - aj
                if (ni1 in 0..<n && nj1 in 0..<m && map[ni1][nj1] != '#') {
                    map[ni1][nj1] = '#'
                    ans++
                }

                val ni2 = 2 * ai - bi
                val nj2 = 2 * aj - bj
                if (ni2 in 0..<n && nj2 in 0..<m && map[ni2][nj2] != '#') {
                    map[ni2][nj2] = '#'
                    ans++
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        val n = map.size
        val m = map[0].size
        check(map.all { it.size == m })
        val charMap = HashMap<Char, ArrayList<Pair<Int, Int>>>()

        for (i in 0..<n) for (j in 0..<m) {
            val c = map[i][j]
            if (c == '.') continue
            charMap.getOrPut(c) { ArrayList() }.add(Pair(i, j))
        }

        var ans = 0
        for ((_, arr) in charMap) {
            for (a in arr.indices) for (b in (a + 1)..<arr.size) {
                val (ai, aj) = arr[a]
                val (bi, bj) = arr[b]
                val di = bi - ai
                val dj = bj - aj

                var ni = bi
                var nj = bj
                while (ni in 0..<n && nj in 0..<m) {
                    if (map[ni][nj] != '#') {
                        map[ni][nj] = '#'
                        ans++
                    }
                    ni += di
                    nj += dj
                }

                ni = ai
                nj = aj
                while (ni in 0..<n && nj in 0..<m) {
                    if (map[ni][nj] != '#') {
                        map[ni][nj] = '#'
                        ans++
                    }
                    ni -= di
                    nj -= dj
                }
            }
        }
        return ans
    }

    val testcase = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent().lines()

    check(part1(testcase).println() == 14)
    check(part2(testcase).println() == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}