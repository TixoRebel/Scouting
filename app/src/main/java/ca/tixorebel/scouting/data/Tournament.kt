package ca.tixorebel.scouting.data

import ca.tixorebel.scouting.data.robotevents.TournamentMetadata
import java.util.*

class Tournament() {
    var matches: List<Match> = LinkedList()

    var teams: Array<Team> = emptyArray()

    var name = ""

    var rawMetadata: TournamentMetadata? = null
}