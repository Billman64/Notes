package com.github.billman64.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.billman64.notes.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val TAG = "Notes-" + this.javaClass.simpleName
    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)




      return binding.root

    }

    private fun mockData(): ArrayList<Note> {
        var list = ArrayList<Note>()
        list.add(Note("Groceries", "Cereal, milk, eggs, butter, pancake mix"))
        list.add(Note("Car maintenance", "Car wash. Oil change. Change air filter. Renew registration. Renew license."))
        list.add(Note("Chores", "1. Dusting 2. Vacuum 3. Mop kitchen 4. Take out garbage."))
        list.add(Note("Study Android concepts","Courses on Udacity, Udemy, Coursera. Google I/O 2024. Dagger. Fragments. MVVM. NavGraph. Git. Compose. ML and AI."))

        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


        // Load data into recyclerView (mock data for now)
        val recyclerView = binding.recyclerview     //TODO: fix inflate error
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        var noteList = mockData()   // populate with mock data, for now //TODO: read from a SQLite database


        var adapter = NotesAdapter(noteList)

        adapter.setOnClickListener(object:NotesAdapter.OnClickListener{ //TODO: figure out why this listener is not responding
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












    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}