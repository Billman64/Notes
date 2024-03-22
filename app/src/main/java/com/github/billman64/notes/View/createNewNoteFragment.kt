package com.github.billman64.notes.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.billman64.notes.Model.DbHelper
import com.github.billman64.notes.ViewModel.SharedViewModel
import com.github.billman64.notes.databinding.FragmentCreateBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class createNewNoteFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentCreateBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var dbH: DbHelper? =
        this.context?.let { DbHelper(it) } //TODO: fix error - not attached to a context
    private val binding get() = this._binding!!

//    private var vm = SharedViewModel()      // Reference shared ViewModel to receive Note detail data from (without depending on a fragment lifecycle)
        private var vm: SharedViewModel = SharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater,container, false)

        // Save button
        val button = binding.saveButton
        button.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "save button clicked")

            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            Log.d(TAG, "Saving $title...")
            dbH?.newRecord(title, content)   // Save input data to database via the database helper object.
            Log.d(TAG, " $title saved.")
        })

      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}