package ca.tixorebel.scouting

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.tixorebel.scouting.data.currentTournament
import kotlinx.android.synthetic.main.fragment_matches.*

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
        if (currentTournament != null)
            matchesAdapter = MatchListAdapter(context, R.layout.tournament_list_layout, currentTournament!!.matches.values.toList())
    }
    var matchesAdapter: MatchListAdapter? = null

    override fun toString(): String { return "Matches" }
}