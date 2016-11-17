package ca.tixorebel.scouting.data

import ca.tixorebel.scouting.data.robotevents.TournamentMetadata
import ca.tixorebel.scouting.data.robotevents.TournamentResults
import java.util.*

class Tournament() {
    var matches: List<Match> = LinkedList()

    var teams: Array<Team> = emptyArray()

    var name = ""

    var rawMetadata: TournamentMetadata? = null

    var rawResultsData: TournamentResults? = null
}