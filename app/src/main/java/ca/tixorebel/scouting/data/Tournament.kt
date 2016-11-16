package ca.tixorebel.scouting.data

import java.util.*

class Tournament() {
    var matches: List<Match> = LinkedList()
        get() { return field }
        set(value) { field = value; shouldPopulateTeams = true }

    val teams: HashMap<String, Team> = HashMap()
        get() {
            if (shouldPopulateTeams) {
                for (m in matches) {
                    if (m.red1 != null) field[m.red1!!.id] = m.red1!!
                    if (m.red2 != null) field[m.red2!!.id] = m.red2!!
                    if (m.blue1 != null) field[m.blue1!!.id] = m.blue1!!
                    if (m.blue2 != null) field[m.blue2!!.id] = m.blue2!!
                    if (m.red3 != null) field[m.red3!!.id] = m.red3!!
                    if (m.blue3 != null) field[m.blue3!!.id] = m.blue3!!
                    if (m.redSitting != null) field[m.redSitting!!.id] = m.redSitting!!
                    if (m.blueSitting != null) field[m.blueSitting!!.id] = m.blueSitting!!
                }
                shouldPopulateTeams = false
            }
            return field
        }
    var shouldPopulateTeams = true

    var name = ""
}