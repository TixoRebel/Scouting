package ca.tixorebel.scouting

import android.widget.ArrayAdapter
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import ca.tixorebel.scouting.data.Tournament
import kotlinx.android.synthetic.main.tournament_list_layout.view.*

class TournamentListAdapter : ArrayAdapter<Tournament> {
    constructor(context: Context, textViewResourceId: Int) : super(context, textViewResourceId)
    constructor(context: Context, resource: Int, items: List<Tournament>) : super(context, resource, items)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val p = getItem(position)
        val vi: LayoutInflater
        vi = LayoutInflater.from(context)
        val v = convertView ?: vi.inflate(R.layout.tournament_list_layout, null)

        if (p != null) {
            v.main_text.text = p.name
        }

        return v
    }

}