package com.github.billman64.notes.View

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.billman64.notes.Model.Note
import com.github.billman64.notes.R

class NotesAdapter(private val noteList:ArrayList<Note>): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
//class NotesAdapter(private val noteList:ArrayList<Note>, private val itemClickListener: (note:Note) -> Unit): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    val TAG:String = "NotesApp-" + this.javaClass.simpleName

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
        val previewThreshold = 40
        Log.d(TAG, "notesAdapter - item content: ${currentItem.content}")

        // Establish preview text, of proper size
        var preview = currentItem.content
        if(preview.length<previewThreshold) preview = preview.subSequence(0,preview.length).toString()

         previewView.text = preview
        if(currentItem.content.length>=previewThreshold) previewView.text = "${previewView.text.toString()}..."

        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.fade_translate
        )
            // The less is done here, the better for performance since this will be called frequently.

        holder.itemView.setOnClickListener(View.OnClickListener {
            val title = titleView.text.toString()
//            var content = previewView.text.toString()
            Log.d(TAG, "item clicked: ${title} ")

//            var vm = SharedViewModel()
//            vm.updateTitle(title)

//            itemClickListener(Note(title, content))
//            SharedViewModel().updateTitle(title)
//            SharedViewModel().updateContent(content)


//            Log.d(TAG, "vm.title: ${vm.title.value.toString()}")


            // Navigate to 2nd fragment to show content of selected note
                //TODO: get proper note id for note-specific fragment


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