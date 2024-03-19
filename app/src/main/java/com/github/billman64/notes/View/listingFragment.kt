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
import com.github.billman64.notes.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class listingFragment : Fragment() {

    private val TAG = "Notes-" + this.javaClass.simpleName
    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val vm: ViewModel = SharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)




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
            MODE_PRIVATE, null)

        Log.d(TAG, "db loading from helper...")
        var noteList = dbH.loadData()


        // testing purposes only
//        dbH.newRecord("test", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//        dbH.countRows()



        var adapter = NotesAdapter(noteList)

        adapter.setOnClickListener(object: NotesAdapter.OnClickListener { //TODO: figure out why this listener is not responding
            override fun onClick(position: Int, model: Note) {
                Log.d(TAG, "adapter clicked!")

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        } )
        binding.recyclerview.adapter = adapter


//        binding.recyclerview.setOnClickListener {
//            Log.d(TAG, "recyclerView tapped")
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

        Log.d(TAG, "recyclerView - item count: ${binding.recyclerview.adapter?.itemCount}")


        val vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

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

