package ca.tixorebel.scouting.data

import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val tournaments = ArrayList<Tournament>(1)
val currentTournament = -1

fun loadCSV(file: File): HashMap<Long, Match>? {
    val r = file.inputStream().bufferedReader()
    val layout = r.readCSVLine() ?: return null

    val generator = LinkedList<(String, Match) -> Unit>()
    for (c in layout) {
        when (c) {
            "Match" -> { generator.add { s, m ->
                arrayOf("Qualifier #", "QF #", "SF #", "Final #").forEach { i -> if (s.startsWith(i)) { extractNumber(i, m); return@forEach } } } }

            "Start Time" -> { generator.add { s, m ->
                if (s != "N/A") { m.startTime = SimpleDateFormat("yyyy-MM-dd  h:mm:ss a", Locale.getDefault()).parse(s) } } }

            "Winner" -> { generator.add { s, m ->
                m.winner = Match.Winner.valueOf(s) } }

            "Red Team (1)" -> { generator.add { s, m -> m.red1 = Team(s, "") } }
            "Red Team (2)" -> { generator.add { s, m -> m.red2 = Team(s, "") } }
            "Red Team (3)" -> { generator.add { s, m -> m.red3 = Team(s, "") } }
            "Red Team (Sitting)" -> { generator.add { s, m -> m.redSitting = Team(s, "") } }

            "Blue Team (1)" -> { generator.add { s, m -> m.blue1 = Team(s, "") } }
            "Blue Team (2)" -> { generator.add { s, m -> m.blue2 = Team(s, "") } }
            "Blue Team (3)" -> { generator.add { s, m -> m.blue3 = Team(s, "") } }
            "Blue Team (Sitting)" -> { generator.add { s, m -> m.blueSitting = Team(s, "") } }

            "Red Score" -> { generator.add { s, m -> m.redScore = s.toLong() } }
            "Blue Score" -> { generator.add { s, m -> m.blueScore = s.toLong() } }
        }
    }

    val matches: HashMap<Long, Match> = HashMap()
    while (true) {
        val line = r.readCSVLine() ?: break
        if (line.size != generator.size) continue
        val i = generator.iterator()
        val match = Match()
        for (c in line) i.next()(c, match)
        if (match.number == -1L) continue
        matches[match.number] = match
    }

    return if (matches.size > 0) matches else null
}

fun extractNumber(s: String, m: Match) { m.number = s.substring(s.length).toLong(); m.type = s.substringBefore(' ', s) }

fun BufferedReader.readCSVLine(): List<String>? = this.readLine()?.split(',')