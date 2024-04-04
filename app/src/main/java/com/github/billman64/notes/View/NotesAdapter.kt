package com.github.billman64.notes.View

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.billman64.notes.Model.DbHelper
import com.github.billman64.notes.Model.Note
import com.github.billman64.notes.Model.Utilities
import com.github.billman64.notes.R
import com.github.billman64.notes.ViewModel.SharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotesAdapter(private val noteList:ArrayList<Note>, private var vm: ViewModel, private var c: Context
    ): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
//class NotesAdapter(private val noteList:ArrayList<Note>, private val itemClickListener: (note:Note) -> Unit): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    val TAG:String = "NotesApp-" + this.javaClass.simpleName
//    private var vm:SharedViewModel = SharedViewModel()
//    interface ItemClickListener {
//        fun onItemClick(position: Int)
//    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

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

        // Establish preview text, of proper size
        val preview = currentItem.content
        val u:Utilities = Utilities()
        val previewThreshold = 70
         previewView.text = u.cutText(preview, previewThreshold)

        // Animation
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.fade_translate
        )
        // The less is done here, the better for performance since this will be called frequently.


        // ClickListener - Navigates go to detail view of note
        holder.itemView.setOnClickListener(View.OnClickListener {
//            val id:Int = position   //TODO: make sure the position# matches record id#
            val id:Int = noteList[position].id
            val title = titleView.text.toString()
            val content = currentItem.content
            Log.d(TAG, "item clicked: $title")

            // Update ViewModel
            Log.d(TAG, "vm title before change: ${(vm as SharedViewModel).title.value} | content: ${(vm as SharedViewModel).content.value}")
            (vm as SharedViewModel).setId(id)
            (vm as SharedViewModel).setTitle(title)
            (vm as SharedViewModel).setContent(content)

            // Navigate to 2nd fragment to show content of selected note
             it.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })

        // Delete by longpress
        holder.itemView.setOnLongClickListener {
            Log.d(TAG, "longpress on item ${currentItem.title} (position: $position) detected.")
            holder.itemView.setBackgroundColor(Color.RED)

//            val id = position  //TODO: make sure the position# matches record id#, or pass record id#'s in Note data class.
            val id = noteList[position].id

            val title = titleView.text.toString()

            // Delete confirmation dialog       //TODO: Refactor into re-usable function, call it in other place where user can delete.
            val dialogBuilder = MaterialAlertDialogBuilder(c)
                .setTitle("Delete note confirmation")
                .setMessage("Are you sure you want to delete this note?")
                .setNegativeButton("no",null)
                .setPositiveButton("yes") { dialog,
                    which ->
                    val dbH = DbHelper(c)   // Imported context works for this, FragmentActivity() does not.
                    val deleteResult = dbH.deleteRecord(id)     //TODO: fix - record not found
                    Log.d(TAG, "DeleteResult $deleteResult")

                    Toast.makeText(c, "Note deleted?:\n $title", Toast.LENGTH_SHORT)
                        .show()    //TODO: Use a string resource here
                }
            var confirm = dialogBuilder.show()
            Log.d(TAG, "Delete confirmation dialog shown")

            true
        }
    }



    override fun getItemCount() = noteList.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Note)
    }

}