package ca.tixorebel.scouting.data.robotevents

data class TournamentRanking (val id : Long, val sku : String, val division : Int, val rank : Int, val teamnum : String, val teamname : String, val wins : Int, val losses : Int, val ties : Int, val wp : Int, val ap : Int, val sp : Int, val numplayed : Int, val created_at : String, val updated_at : String, val deleted_at : String)

data class SkillsResult (val id : Long, val sku : String, val rank : String, val team : String, val highscore : Int, val attempts : Int, val type : String, val created_at : String, val updated_at : String, val deleted_at : String)

data class MatchResult (val division: Int, val round : Int, val instance : Int, val matchnum : Int, val timescheduled : String, val red1 : String, val red2 : String, val red3: String, val redscore : Int, val redsit : String, val blue1 : String, val blue2 : String, val blue3 : String, val bluescore : String, val bluesit : String)

data class TournamentResults (val rankings : Array<TournamentRanking>, val finalist_rankings : Array<Any>, val skills : Array<SkillsResult>, val match_results : Array<MatchResult>)
