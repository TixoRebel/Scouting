package ca.tixorebel.scouting

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.tixorebel.scouting.data.Match
import ca.tixorebel.scouting.data.currentTournament
import ca.tixorebel.scouting.data.matchTypes
import kotlinx.android.synthetic.main.fragment_matches.*
import java.util.*

class MatchesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (matchesAdapter != null) match_list.adapter = matchesAdapter

        match_list.setOnItemClickListener { parent, view, position, id ->

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (currentTournament != null) {
            val matchesWithTime = LinkedList<Match>()
            val matchesWithoutTime = LinkedList<Match>()
            for (m in currentTournament!!.matches) if (m.startTime != null) matchesWithTime.add(m) else matchesWithoutTime.add(m)
            matchesWithTime.sortByDescending(Match::startTime)
            val localMatchTypes = HashMap<String, LinkedList<Match>>()
            for (m in matchesWithoutTime) {
                if (localMatchTypes[m.type] == null) localMatchTypes[m.type] = LinkedList()
                localMatchTypes[m.type]!!.add(m)
            }
            val matchesToDisplay = LinkedList<Match>()
            for (m in matchTypes.reversed()) {
                if (localMatchTypes[m] != null) matchesToDisplay.addAll(localMatchTypes[m]!!.sortedByDescending(Match::number))
            }
            matchesToDisplay.addAll(matchesWithTime)
            matchesAdapter = MatchListAdapter(context, R.layout.match_list_layout, matchesToDisplay)
        }
    }
    var matchesAdapter: MatchListAdapter? = null

    override fun toString(): String { return "Matches" }
}