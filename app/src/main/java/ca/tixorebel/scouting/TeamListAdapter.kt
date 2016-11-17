package ca.tixorebel.scouting

import android.widget.ArrayAdapter
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import ca.tixorebel.scouting.data.Team
import kotlinx.android.synthetic.main.team_list_layout.view.*

class TeamListAdapter : ArrayAdapter<Team> {
    constructor(context: Context, textViewResourceId: Int) : super(context, textViewResourceId) { resource = textViewResourceId }
    constructor(context: Context, resource: Int, items: List<Team>) : super(context, resource, items) { this.resource = resource }

    val resource: Int

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val p = getItem(position)
        val vi: LayoutInflater
        vi = LayoutInflater.from(context)
        val v = convertView ?: vi.inflate(resource, null)

        if (p != null) {
            v.team_id_text.text = p.id
        }

        return v
    }

}