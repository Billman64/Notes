package com.github.billman64.notes.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.billman64.notes.Model.DbHelper
import com.github.billman64.notes.ViewModel.SharedViewModel
import com.github.billman64.notes.databinding.FragmentDetailBinding
import dagger.Module

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@Module
class detailFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private var vm = SharedViewModel()      // Reference shared ViewModel to receive Note detail data from (without depending on a fragment lifecycle)
        private var vm: SharedViewModel = SharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        Log.d(TAG, "(frag2) vm.title.value: ${vm?.title?.value.toString()}")
        Log.d(TAG, "(frag2)  vm.content.value: ${vm?.content?.value.toString()}")
        binding.title.text = vm?.title?.value.toString()

//        val vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        vm.title.observe(viewLifecycleOwner, Observer {
            binding.title.text = it
        })
        vm.content.observe(viewLifecycleOwner, Observer {
            binding.content.text = it
        })

      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        Log.d(TAG, "onViewCreated() vm.title: ${vm.title.value.toString()}")
        vm.title.observe(viewLifecycleOwner, Observer {
            binding.title.text = it
        })

        val dbH: DbHelper = DbHelper(view.context)
        var id:Int = 0

        vm.id.observe(viewLifecycleOwner) {
            id = it
        }

        // Delete button
        val deleteButton = binding.deleteButton
        deleteButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "delete button pressed")



            //TODO: delete confirmation dialog

            if(1==1){
                val deleteResult = dbH.deleteRecord(id)
                Log.d(TAG, "DeleteResult $deleteResult")

                Toast.makeText(context,"Note deleted\n ${binding.title.text}", Toast.LENGTH_SHORT).show()  //TODO: Use a string resource here
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // create instance of shared ViewModel
        vm = SharedViewModel()
    }
}