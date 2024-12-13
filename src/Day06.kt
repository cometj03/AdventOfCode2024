fun main() {
    val di = intArrayOf(-1, 0, 1, 0)
    val dj = intArrayOf(0, 1, 0, -1)

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        check(input.all { it.length == m })
        var i0 = -1
        var j0 = -1
        for (i in 0..<n)
            for (j in 0..<m)
                if (input[i][j] == '^') {
                    i0 = i
                    j0 = j
                }

        var cnt = 0
        var d = 0
        val vis = Array(n) { BooleanArray(m) }
        while (true) {
            if (!vis[i0][j0]) cnt++
            vis[i0][j0] = true
            val ii = i0 + di[d]
            val jj = j0 + dj[d]
            if (ii !in 0..<n || jj !in 0..<m) break
            if (input[ii][jj] == '#') {
                d = (d + 1) % 4
                continue
            }
            i0 = ii
            j0 = jj
        }
        return cnt
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        check(input.all { it.length == m })
        var i0 = -1
        var j0 = -1
        for (i in 0..<n)
            for (j in 0..<m)
                if (input[i][j] == '^') {
                    i0 = i
                    j0 = j
                }

        val vis = Array(4) { Array(n) { BooleanArray(m) } }
        var ans = 0
        for (oi in 0..<n) for (oj in 0..<m) {
            if (input[oi][oj] == '#') continue
            var i = i0
            var j = j0
            var d = 0
            vis.forEach { a -> a.forEach { it.fill(false) } }
            while (true) {
                if (vis[d][i][j]) {
                    ans++
                    break
                }
                vis[d][i][j] = true
                val ii = i + di[d]
                val jj = j + dj[d]
                if (ii !in 0..<n || jj !in 0..<m) break
                if (input[ii][jj] == '#' || (ii == oi && jj == oj)) {
                    d = (d + 1) % 4
                    continue
                }
                i = ii
                j = jj
            }
        }
        return ans
    }

    val testcase = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().lines()

    check(part1(testcase).println() == 41)
    check(part2(testcase).println() == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}