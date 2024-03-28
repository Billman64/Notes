package com.github.billman64.notes.View

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.billman64.notes.R
import com.github.billman64.notes.databinding.ActivityMainBinding
import dagger.Module

@Module
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

//    private val viewModel: SharedViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel.updateTitle("Sample Title")
//        viewModel.updateContent("Sample content. List of pizza places: Domino's, Pizza Hut, Jet's Pizza")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Creating new note...", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null)
//                    .setAnchorView(R.id.fab).show()


            switchFragment(createNewNoteFragment())
            navController.navigate(R.id.createNewNoteFragment)
        }



        // Default fragment to the listing fragment.
        switchFragment(createNewNoteFragment())

//        val fm:FragmentManager = supportFragmentManager // supportFragmentManager needed to initialize
//        val fragmentTransaction:FragmentTransaction = fm.beginTransaction()
//            .setCustomAnimations(R.anim.fade_translate,0)   // custom animation
//        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, listingFragment())

        //TODO: Use Dagger for dependency injection with objects, such as the DbHelper


    }

    private fun switchFragment(frag: Fragment){
        val fm:FragmentManager = supportFragmentManager // supportFragmentManager needed to initialize
        val fragmentTransaction:FragmentTransaction = fm.beginTransaction()
            .setCustomAnimations(R.anim.fade_translate,0)   // custom animation
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, frag)
        // .commit causes visual items to freeze in place

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