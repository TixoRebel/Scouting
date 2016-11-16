package ca.tixorebel.scouting

import android.widget.ArrayAdapter
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import ca.tixorebel.scouting.data.Match
import kotlinx.android.synthetic.main.match_list_layout.view.*

class MatchListAdapter : ArrayAdapter<Match> {
    constructor(context: Context, textViewResourceId: Int) : super(context, textViewResourceId) { resource = textViewResourceId }
    constructor(context: Context, resource: Int, items: List<Match>) : super(context, resource, items) { this.resource = resource }

    val resource: Int

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val p = getItem(position)
        val vi: LayoutInflater
        vi = LayoutInflater.from(context)
        val v = convertView ?: vi.inflate(resource, null)

        if (p != null) {
            if ((p.number % 1.0) == 0.0)
                v.number_text.text = p.number.toInt().toString()
            else
                v.number_text.text = p.number.toString().replace('.', '-')
            v.type_text.text = p.type
        }

        return v
    }

}