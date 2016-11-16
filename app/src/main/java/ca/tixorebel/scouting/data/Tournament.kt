package ca.tixorebel.scouting.data

import java.util.*

class Tournament() {
    var matches: List<Match> = LinkedList()

    var teams: HashMap<String, Team> = HashMap()

    var name = ""
}