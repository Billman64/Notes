package com.github.billman64.notes

import android.content.ContentValues.TAG
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.billman64.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateTitle("Sample Title")
        viewModel.updateContent("Sample content. List of pizza places: Domino's, Pizza Hut, Jet's Pizza")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
        }


        // Replace fragment
        //TODO: determine current fragment from ViewModel data

        val fm:FragmentManager = supportFragmentManager // supportFragmentManager needed to initialize
        val fragmentTransaction:FragmentTransaction = fm.beginTransaction()
            .setCustomAnimations(R.anim.fade_translate,0)   // custom animation
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, FirstFragment())
//        fragmentTransaction.commit()  //TODO: fix UI bug that keeps recyclerview initial setup stuck on screen



        //TODO: load any notes from local database (SQLite)


        val db:SQLiteDatabase = openOrCreateDatabase("notesDb", MODE_PRIVATE, null)

        //TODO: check if db exists first. If not, create a new one.
        var sql = "CREATE TABLE IF NOT EXISTS Notes(Id INT, Title VARCHAR, Content, VARCHAR)"
        db.execSQL(sql)

        sql = "SELECT COUNT(*) FROM notes;"
        var numRows = 0
        var strArr = arrayOf<String>()
        var sqlResponse = db.rawQuery(sql, strArr )
        Log.d(TAG, "db notes table row count: ${sqlResponse}")

        if(sqlResponse.toString().all { char -> char.isDigit() }) { // check if response to row count is a number
            numRows = sqlResponse.toString().toInt()
        } else numRows=0

        if(numRows>0) {     // fetch notes from database
            var noteList = ArrayList<Note>()


            for(i in 1..numRows) {
                sql=""
                sqlResponse = db.rawQuery(sql, strArr)

//                noteList.add()



            }


        }










    }
override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}