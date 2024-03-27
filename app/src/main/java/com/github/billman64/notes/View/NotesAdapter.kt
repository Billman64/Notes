package com.github.billman64.notes.View

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.billman64.notes.Model.Note
import com.github.billman64.notes.Model.Utilities
import com.github.billman64.notes.R
import com.github.billman64.notes.ViewModel.SharedViewModel

class NotesAdapter(private val noteList:ArrayList<Note>, private var vm: ViewModel
    ): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
//class NotesAdapter(private val noteList:ArrayList<Note>, private val itemClickListener: (note:Note) -> Unit): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    val TAG:String = "NotesApp-" + this.javaClass.simpleName
//    private var vm:SharedViewModel = SharedViewModel()
//    interface ItemClickListener {
//        fun onItemClick(position: Int)
//    }

    private var onClickListener: OnClickListener? = null

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteView = itemView.findViewById<View>(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentItem = noteList[position]
        val titleView = holder.itemView.findViewById<TextView>(R.id.title)
        titleView.text = currentItem.title
        val previewView = holder.itemView.findViewById<TextView>(R.id.preview)

        Log.d(TAG, "notesAdapter - item content: ${currentItem.content}")

        // Establish preview text, of proper size
        var preview = currentItem.content
        val u:Utilities = Utilities()
        val previewThreshold = 70
         previewView.text = u.cutText(preview, previewThreshold)

        // Animation
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.fade_translate
        )
        // The less is done here, the better for performance since this will be called frequently.


        // ClickListener
        holder.itemView.setOnClickListener(View.OnClickListener {
            val title = titleView.text.toString()
            val content = currentItem.content

            Log.d(TAG, "item clicked: $title")

            // Old code
//            itemClickListener(Note(title, content))
//            SharedViewModel().updateTitle(title)
//            SharedViewModel().updateContent(content)

//            vm = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
//            var vm:SharedViewModel = SharedViewModel()
//            vm = ViewModelProvider()[SharedViewModel::class.java]
//            vm = ViewModelProvider(FragmentActivity())[SharedViewModel::class.java]
//            vm = ViewModelProvider(RequireActivity())[SharedViewModel::class.java]    // RequireActivity() not recognized in Adapter

            // Update ViewModel
            Log.d(TAG, "vm title before change: ${(vm as SharedViewModel).title.value} | content: ${(vm as SharedViewModel).content.value}")
            (vm as SharedViewModel).setTitle(title)
            (vm as SharedViewModel).setContent(content)

            // Navigate to 2nd fragment to show content of selected note
             it.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })
    }

    override fun getItemCount() = noteList.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Note)
    }

}