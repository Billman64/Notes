package com.github.billman64.notes.View

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.billman64.notes.Model.DbHelper
import com.github.billman64.notes.Model.Note
import com.github.billman64.notes.R
import com.github.billman64.notes.ViewModel.SharedViewModel
import com.github.billman64.notes.databinding.FragmentListingBinding
import dagger.Module

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@Module
class listingFragment : Fragment() {

    private val TAG = "Notes-" + this.javaClass.simpleName
    private var _binding: FragmentListingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private val vm: ViewModel = SharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        lateinit var vm:SharedViewModel
        vm = ViewModelProvider(this).get(SharedViewModel::class.java)

        _binding = FragmentListingBinding.inflate(inflater, container, false)

      return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vm = SharedViewModel()

        Log.d(TAG, "(frag1) vm.title: ${vm.title.value.toString()}")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load data into recyclerView (mock data for now)

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val dbH = DbHelper(requireContext())

        Log.d(TAG, "about to load db from fragment...")
        val db: SQLiteDatabase = requireActivity().openOrCreateDatabase("notesDb",
            MODE_PRIVATE, null)     //TODO: refactor using DbHelper


        // TESTING purposes only
//        dbH.newRecord("Drive for Uber", "Look for events in the Cbus area.")
//        dbH.updateRecord(dbH.countRows()-1, "Drive Lyft instead", "Check out the Cbus area.")
//        Log.d(TAG, "Record updated!")
//        var a = dbH.readRecord(3000)
//        Log.d(TAG, "dbH.readRecord(2): ${a.title} | ${a.content}")


        // Initialize/get ViewModel
        var vm:SharedViewModel = SharedViewModel()
        vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        Log.d(TAG, "ViewModel testing - title: ${vm.title.value.toString()} content: ${vm.content.value}")

        // Set up adapter and recyclerView
        var noteList = dbH.loadData()
        Log.d(TAG, "db loaded from helper. Count: ${noteList.count()}")
        var adapter = NotesAdapter(noteList, vm, this.requireContext())
        binding.recyclerview.adapter = adapter

        Log.d(TAG, "recyclerView - item count: ${binding.recyclerview.adapter?.itemCount}")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Deprecated
    private fun mockDataNoDb(): ArrayList<Note> {
        var list = ArrayList<Note>()
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))

        return list
    }
}

