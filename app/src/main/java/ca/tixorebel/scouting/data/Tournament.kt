package ca.tixorebel.scouting.data

import java.util.*

class Tournament() {
    var matches: List<Match> = LinkedList()
        get() { return field }
        set(value) { field = value; shouldPopulateTeams = true }

    val teams: List<Team> = listOf()
        get() {
            if (shouldPopulateTeams) {
                val teamsTemp = HashMap<String, Team>()
                for (m in matches) {
                    if (m.red1 != null) teamsTemp[m.red1!!.id] = m.red1!!
                    if (m.red2 != null) teamsTemp[m.red2!!.id] = m.red2!!
                    if (m.blue1 != null) teamsTemp[m.blue1!!.id] = m.blue1!!
                    if (m.blue2 != null) teamsTemp[m.blue2!!.id] = m.blue2!!
                    if (m.red3 != null) teamsTemp[m.red3!!.id] = m.red3!!
                    if (m.blue3 != null) teamsTemp[m.blue3!!.id] = m.blue3!!
                    if (m.redSitting != null) teamsTemp[m.redSitting!!.id] = m.redSitting!!
                    if (m.blueSitting != null) teamsTemp[m.blueSitting!!.id] = m.blueSitting!!
                }
                field = teamsTemp.values.toList()
                shouldPopulateTeams = false
            }
            return field
        }
    var shouldPopulateTeams = true

    var name = ""
}