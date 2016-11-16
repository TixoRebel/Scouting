package ca.tixorebel.scouting

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.tixorebel.scouting.data.Team
import ca.tixorebel.scouting.data.currentTournament
import kotlinx.android.synthetic.main.fragment_matches.*
import kotlinx.android.synthetic.main.fragment_teams.*
import kotlin.comparisons.compareBy

class TeamsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (teamsAdapter != null) team_list.adapter = teamsAdapter

        team_list.setOnItemClickListener { parent, view, position, id ->

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (currentTournament != null) {
            teamsAdapter = TeamListAdapter(context, R.layout.team_list_layout, currentTournament!!.teams.sortedWith(compareBy(Team::number, Team::letter)))
        }
    }
    var teamsAdapter: TeamListAdapter? = null

    override fun toString(): String { return "Teams" }
}