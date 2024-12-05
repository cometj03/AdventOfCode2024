typealias Graph = HashMap<Int, IntArray>

fun main() {
    fun subGraph(graph: Graph, nodes: List<Int>): Graph {
        val subGraph: Graph = hashMapOf()
        nodes.forEach { n ->
            subGraph[n] = nodes.filter { graph[n]?.contains(it) ?: false }.toIntArray()
        }
        return subGraph
    }

    fun isSorted(graph: Graph, list: List<Int>): Boolean {
        val inDegree = IntArray(100) { 0 }
        graph.forEach { (_, nodes) ->
            nodes.forEach { v -> inDegree[v]++ }
        }
        list.forEach { n ->
            if (inDegree[n] != 0) return false
            graph[n]?.forEach { v -> inDegree[v]-- }
        }
        return true
    }

    fun sort(graph: Graph, list: List<Int>): List<Int> {
        val inDegree = IntArray(100) { 0 }
        val vis = IntArray(100) { 0 }
        val ret = mutableListOf<Int>()

        graph.forEach { (_, nodes) ->
            nodes.forEach { v -> inDegree[v]++ }
        }

        list.indices.forEach { _ ->
            val n = list.find { vis[it] == 0 && inDegree[it] == 0 } ?: error("")
            graph[n]?.forEach { inDegree[it]-- }
            ret.add(n)
            vis[n] = 1
        }
        return ret
    }

    fun part1(input: List<String>): Int {
        val A = input.indexOf("")
        val rules = input.subList(0, A).map { it.split("|").map(String::toInt) }
        val samples = input.subList(A + 1, input.size).map { it.split(",").map(String::toInt) }

        val graph: Graph = hashMapOf()
        rules.forEach { (u, v) ->
            graph[u] = graph.getOrElse(u) { intArrayOf() } + intArrayOf(v)
        }

        var ans = 0
        samples.forEach { sample ->
            val subGraph = subGraph(graph, sample)
            if (isSorted(subGraph, sample))
                ans += sample[sample.size / 2]
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val A = input.indexOf("")
        val rules = input.subList(0, A).map { it.split("|").map(String::toInt) }
        val samples = input.subList(A + 1, input.size).map { it.split(",").map(String::toInt) }

        val graph: Graph = hashMapOf()
        rules.forEach { (u, v) ->
            graph[u] = graph.getOrElse(u) { intArrayOf() } + intArrayOf(v)
        }

        var ans = 0
        samples.forEach { sample ->
            val subGraph = subGraph(graph, sample)
            if (!isSorted(subGraph, sample)) {
                val sortedList = sort(subGraph, sample)
                ans += sortedList[sortedList.size / 2]
            }
        }
        return ans
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

    check(part1(testcase) == 143)
    check(part2(testcase) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}