package ca.tixorebel.scouting.data.robotevents

import ca.tixorebel.scouting.data.Match
import ca.tixorebel.scouting.data.Team
import ca.tixorebel.scouting.data.Tournament
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URL
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer

object API {
    var tournaments : List<TournamentMetadata> = emptyList()

    fun loadTournamentsMetadata(done : Consumer<Exception>) = Thread {
        try {
            tournaments = Regex("""<tr>.*?<td>.*?([A-Z].*\w).*?</td>.*?<td>.*?([0-9]*).*?</td>.*?<td>.*?(RE-VRC-.{7}).*?</td>.*?<td.*?>(.*?)</td>.*?<td>.*?([A-Z].*\w).*?</td><td>(.*?)</td><td>.*?>.*?(\w.*\w).*?</td>.*?</tr>""").findAll(URL("https://www.robotevents.com/robot-competitions/vex-robotics-competition").readText()).map { TournamentMetadata(it.groupValues[0], it.groupValues[1].toInt(), it.groupValues[2], it.groupValues[3], it.groupValues[4], TournamentType.valueOf(it.groupValues[5]), it.groupValues[6]) }.toList()
            done.accept(null)
        } catch (e : Exception) {
            done.accept(e)
        }
    }.start()

    fun getTournamentdata(tournament : TournamentMetadata, done : BiConsumer<Exception, Tournament>) = Thread {
        val result = Tournament()
        result.name = tournament.name;
        val mapper = jacksonObjectMapper()
        val html = URL("https://www.robotevents.com/robot-competitions/vex-robotics-competition/${tournament.eventCode}.html").readText()
        val results = mapper.readValue(Regex("var results = (.*?);").find(html)!!.groupValues[0], TournamentResults::class.java)
        result.teams = HashMap(results.rankings.associate { Pair(it.teamnum, Team(it.teamnum, it.teamname)) })
        result.matches = results.match_results.map {
            val match = Match()
            match
        }
        done.accept(null, result)
    }.start()
}