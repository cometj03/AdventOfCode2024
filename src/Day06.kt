fun main() {
    val direction: List<Pair<Int, Int>> = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

    fun parseInput(input: List<String>): Triple<List<IntArray>, Int, Int> {
        var starti = 0
        var startj = 0
        val map = input.mapIndexed { i, line ->
            line.mapIndexed { j, c ->
                if (c == '^') {
                    starti = i
                    startj = j
                }
                if (c == '#') -1 else 0
            }.toIntArray()
        }
        return Triple(map, starti, startj)
    }

    tailrec fun moveForward(map: List<IntArray>, i: Int, j: Int, dir: Int): Triple<Boolean, Int, Int> {
        map[i][j] = 1
        val ii = i + direction[dir].first
        val jj = j + direction[dir].second
        if (ii !in map.indices || jj !in map.first().indices)
            return Triple(false, 0, 0)
        if (map[ii][jj] == -1)
            return Triple(true, i, j)
        return moveForward(map, ii, jj, dir)
    }

    tailrec fun move(map: List<IntArray>, i: Int, j: Int, dir: Int = 0) {
        val (obstacle, ii, jj) = moveForward(map, i, j, dir)
        if (obstacle) move(map, ii, jj, (dir + 1) % 4) // change direction
    }

    tailrec fun isLoop(map: List<IntArray>, vis: Array<Array<IntArray>>, i: Int, j: Int, dir: Int = 0): Boolean {
        val (obstacle, ii, jj) = moveForward(map, i, j, dir)
        if (!obstacle) return false
        // already visited
        if (vis[ii][jj][dir] == 1) return true
        // remember position and dir
        vis[ii][jj][dir] = 1
        return isLoop(map, vis, ii, jj, (dir + 1) % 4)
    }

    fun part1(input: List<String>): Int {
        check(input.all { it.length == input.first().length })
        val (map, guardi, guardj) = parseInput(input)
        move(map, guardi, guardj, 0)
        return map.sumOf { it.count { i -> i == 1 } }
    }

    fun part2(input: List<String>): Int {
        check(input.all { it.length == input.first().length })
        val (map, guardi, guardj) = parseInput(input)

        var ans = 0
        val vis = Array(map.size) { Array(map.first().size) { IntArray(4) } }

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == -1 || i == guardi && j == guardj) continue
                // clear vis
                vis.forEach { arr ->
                    arr.forEach { it.indices.forEach { i -> it[i] = 0 } }
                }
                map[i][j] = -1
                if (isLoop(map, vis, guardi, guardj))
                    ans++
                map[i][j] = 0
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