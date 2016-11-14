package ca.tixorebel.scouting.data

import java.util.*

class Match {
    var red1: Team? = null
    var red2: Team? = null
    var red3: Team? = null
    var blue1: Team? = null
    var blue2: Team? = null
    var blue3: Team? = null
    var redSitting: Team? = null
    var blueSitting: Team? = null
    var number: Double = -1.0
    var type: String = ""
    var redScore: Long = -1
    var blueScore: Long = -1
    var startTime: Date? = null
    var winner: Winner? = null

    enum class Winner { blue, red, tie }
}