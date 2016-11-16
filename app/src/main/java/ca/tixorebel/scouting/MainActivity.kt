package ca.tixorebel.scouting

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.widget.Toast
import ca.tixorebel.scouting.data.Tournament
import ca.tixorebel.scouting.data.loadCSV
import ca.tixorebel.scouting.data.tournaments
import android.provider.OpenableColumns
import ca.tixorebel.scouting.data.Match

class MainActivity : AppCompatActivity() {
    private var fragments: Array<Fragment> = arrayOf(TournamentsFragment(), MatchesFragment(), TeamsFragment())
    val LOAD_CSV_ID = 278

    fun navigateTo(id: Int) = fragmentManager.beginTransaction().replace(R.id.content_frame, fragments[id]).commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(0)

        left_drawer.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fragments.map(Fragment::toString))
        left_drawer.onItemClickListener = AdapterView.OnItemClickListener {  parent, view, position, id ->
            navigateTo(position)
            drawer_layout.closeDrawer(left_drawer)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_loadCSV -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "text/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Parse"), LOAD_CSV_ID)
                }
                catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOAD_CSV_ID -> if (resultCode == Activity.RESULT_OK && data?.data != null) {
                val uri = data!!.data
                val matches: List<Match>
                try { matches = loadCSV(contentResolver.openInputStream(uri)) ?: return }
                catch (e: Exception) {
                    Toast.makeText(this, "Failed to parse csv", Toast.LENGTH_SHORT).show()
                    return
                }
                val tournament = Tournament()
                tournament.matches = matches
                val returnCursor = contentResolver.query(uri, null, null, null, null)
                val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                returnCursor.moveToFirst()
                tournament.name = returnCursor.getString(nameIndex)
                returnCursor.close()
                tournaments.add(tournament)
                (fragments[0] as TournamentsFragment).tournamentsAdapter?.notifyDataSetChanged()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
