package ca.tixorebel.scouting.data.robotevents

import ca.tixorebel.scouting.data.Match
import ca.tixorebel.scouting.data.Team
import ca.tixorebel.scouting.data.Tournament
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer

object API {
    var tournaments: List<TournamentMetadata> = emptyList()

    fun loadTournamentsMetadata(done: Consumer<Exception>) = Thread {
        try {
            tournaments = Regex("""<tr>.*?<td>.*?([A-Z].*\w).*?</td>.*?<td>.*?([0-9]*).*?</td>.*?<td>.*?(RE-VRC-.{7}).*?</td>.*?<td.*?>(.*?)</td>.*?<td>.*?([A-Z].*\w).*?</td><td>(.*?)</td><td>.*?>.*?(\w.*\w).*?</td>.*?</tr>""").findAll(URL("https://www.robotevents.com/robot-competitions/vex-robotics-competition").readText()).map { TournamentMetadata(it.groupValues[0], it.groupValues[1].toInt(), it.groupValues[2], it.groupValues[3], it.groupValues[4], it.groupValues[5], it.groupValues[6]) }.toList()
            done.accept(null)
        } catch (e: Exception) {
            done.accept(e)
        }
    }.start()

    fun getTournamentdata(tournament: TournamentMetadata, done: BiConsumer<Exception, Tournament>) = Thread {
        val result = Tournament()
        try {
            result.rawMetadata = tournament
            result.name = tournament.name
            val results = jacksonObjectMapper().readValue(Regex("var results = (.*?);").find(URL("https://www.robotevents.com/robot-competitions/vex-robotics-competition/${tournament.eventCode}.html").readText())!!.groupValues[0], TournamentResults::class.java)
            result.rawResultsData = results
            val teams = results.rankings.associate { Pair(it.teamnum, Team(it.teamnum, it.teamname)) }
            val matchTypes = mapOf(Pair(2, "Qualifier"), Pair(3, "QF"), Pair(4, "SF"), Pair(5, "Final"))
            result.matches = results.match_results.map {
                val match = Match()
                match.rawData = it
                match.red1 = teams[it.red1]
                match.red2 = teams[it.red2]
                match.red3 = teams[it.red3]
                match.redScore = it.redscore
                match.redSitting = teams[it.redsit]
                match.blue1 = teams[it.blue1]
                match.blue2 = teams[it.blue2]
                match.blue3 = teams[it.blue3]
                match.blueScore = it.bluescore
                match.blueSitting = teams[it.bluesit]
                match.winner = if (it.redscore == it.bluescore) Match.Winner.tie else if (it.redscore > it.bluescore) Match.Winner.red else Match.Winner.blue
                match.startTime = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US).parse(it.timescheduled)
                match.type = matchTypes[it.round]!!
                match.number = if (it.round == 2) it.matchnum.toDouble() else it.instance + it.matchnum * 0.1
                match
            }
            result.teams = teams.values.toTypedArray()
            done.accept(null, result)
        } catch (e: Exception) {
            done.accept(e, result)
        }
    }.start()
}