package ca.tixorebel.scouting

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.tixorebel.scouting.data.*
import kotlinx.android.synthetic.main.fragment_tournament.*

class TournamentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (tournamentsAdapter != null) tournament_list.adapter = tournamentsAdapter
    }

    override fun toString(): String { return "Tournaments" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tournamentsAdapter = TournamentListAdapter(context, R.layout.tournament_list_layout, tournaments)
    }
    var tournamentsAdapter: TournamentListAdapter? = null
}