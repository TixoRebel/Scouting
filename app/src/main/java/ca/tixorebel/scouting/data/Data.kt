package ca.tixorebel.scouting.data

import java.io.BufferedReader
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

val tournaments = ArrayList<Tournament>(1)
var currentTournament: Tournament? = null
val matchTypes = arrayOf("Qualifier", "QF", "SF", "Final")

fun loadCSV(input: InputStream): LinkedList<Match>? {
    val r = input.bufferedReader()
    val layout = r.readCSVLine() ?: return null

    val generator = LinkedList<(String, Match) -> Unit>()
    for (c in layout) {
        when (c.trim('"')) {
            "Match" -> { generator.add { s, m ->
                matchTypes.forEach { i -> if (s.startsWith(i)) { extractNumber(i + " #", s, m); return@forEach } } } }

            "Start Time" -> { generator.add { s, m ->
                if (s != "N/A") { m.startTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(s) } } }

            "Winner" -> { generator.add { s, m ->
                m.winner = Match.Winner.valueOf(s) } }

            "Red Team (1)" -> { generator.add { s, m -> if (s != "None") m.red1 = Team(s, "") } }
            "Red Team (2)" -> { generator.add { s, m -> if (s != "None") m.red2 = Team(s, "") } }
            "Red Team (3)" -> { generator.add { s, m -> if (s != "None") m.red3 = Team(s, "") } }
            "Red Team (Sitting)" -> { generator.add { s, m -> if (s != "None") m.redSitting = Team(s, "") } }

            "Blue Team (1)" -> { generator.add { s, m -> if (s != "None") m.blue1 = Team(s, "") } }
            "Blue Team (2)" -> { generator.add { s, m -> if (s != "None") m.blue2 = Team(s, "") } }
            "Blue Team (3)" -> { generator.add { s, m -> if (s != "None") m.blue3 = Team(s, "") } }
            "Blue Team (Sitting)" -> { generator.add { s, m -> if (s != "None") m.blueSitting = Team(s, "") } }

            "Red Score" -> { generator.add { s, m -> m.redScore = s.toLong() } }
            "Blue Score" -> { generator.add { s, m -> m.blueScore = s.toLong() } }
        }
    }

    val matches = LinkedList<Match>()
    while (true) {
        val line = r.readCSVLine() ?: break
        if (line.size != generator.size) continue
        val i = generator.iterator()
        val match = Match()
        for (c in line) i.next()(c.trim('"'), match)
        if (match.number == -1.0) continue
        matches.add(match)
    }

    return if (matches.size > 0) matches else null
}

fun extractNumber(t: String, s: String, m: Match) { m.number = s.substring(t.length).replace('-', '.').toDouble(); m.type = s.substringBefore(' ', s) }

fun BufferedReader.readCSVLine(): List<String>? = this.readLine()?.split(',')