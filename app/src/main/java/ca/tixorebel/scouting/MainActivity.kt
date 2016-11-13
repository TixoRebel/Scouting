package ca.tixorebel.scouting

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.MimeTypeMap
import android.content.Intent
import android.net.Uri
import java.io.File
import android.content.Intent
import android.widget.Toast




class MainActivity : AppCompatActivity() {
    private var fragments: Array<Fragment> = arrayOf(TournamentFragment(), TeamFragment(), MatchFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragments[0]).commit()

        left_drawer.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fragments.map(Fragment::toString))
        left_drawer.onItemClickListener = AdapterView.OnItemClickListener() {  parent, view, position, id ->
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragments[position]).commit()
            drawer_layout.closeDrawer(left_drawer)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_loadCSV -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Parse"), FILE_SELECT_CODE)
                }
                catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
