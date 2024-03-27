package com.github.billman64.notes.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.billman64.notes.ViewModel.SharedViewModel
import com.github.billman64.notes.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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