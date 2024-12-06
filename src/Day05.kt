typealias Graph = Map<Int, Set<Int>>

fun main() {
    // return a subgraph with `nodes` from `graph`
    fun subGraph(graph: Graph, nodes: List<Int>): Graph {
        return nodes.associateWith { graph[it]?.intersect(nodes.toSet()) ?: emptySet() }
    }

    fun isSorted(graph: Graph, list: List<Int>): Boolean {
        val inDegree = IntArray(100) { 0 }
        for ((_, nodes) in graph) {
            for (n in nodes) inDegree[n]++
        }
        for (i in list) {
            if (inDegree[i] != 0) return false
            graph[i]?.forEach { n -> inDegree[n]-- }
        }
        return true
    }

    fun sort(graph: Graph, list: List<Int>): List<Int> {
        val inDegree = IntArray(100) { 0 }
        val vis = IntArray(100) { 0 }

        for ((_, nodes) in graph) {
            for (n in nodes) inDegree[n]++
        }

        return list.map {
            val n = list.find { vis[it] == 0 && inDegree[it] == 0 } ?: error("")
            graph[n]?.forEach { next -> inDegree[next]-- }
            vis[n] = 1
            n
        }
    }

    fun part1(input: List<String>): Int {
        val idx = input.indexOf("")
        // before|after1, before|after2 ...
        // => { before: [after1, after2, ...] }
        val rules: Graph = input.subList(0, idx)
            .map { it.split("|").map(String::toInt) }
            .groupBy({ it[0] }, { it[1] })
            .mapValues { it.value.toSet() }
        val updates = input.subList(idx + 1, input.size).map { it.split(",").map(String::toInt) }

        return updates
            .filter { line ->
                val subGraph = subGraph(rules, line)
                isSorted(subGraph, line)
            }
            .sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val idx = input.indexOf("")
        val rules: Graph = input.subList(0, idx)
            .map { it.split("|").map(String::toInt) }
            .groupBy({ it[0] }, { it[1] })
            .mapValues { it.value.toSet() }
        val updates = input.subList(idx + 1, input.size).map { it.split(",").map(String::toInt) }

        return updates
            .mapNotNull { line ->
                val subGraph = subGraph(rules, line)
                if (isSorted(subGraph, line)) null else sort(subGraph, line)
            }
            .sumOf { it[it.size / 2] }
    }

    val testcase = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent().lines()

    check(part1(testcase).println() == 143)
    check(part2(testcase).println() == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}