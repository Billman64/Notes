package com.github.billman64.notes

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }


        // Load data into recyclerView (mock data for now)
        val recyclerView = binding.recyclerview     //TODO: fix inflate error
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        var noteList = loadData()


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


        val vm = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    }

    private fun loadData(): ArrayList<Note> {

        // Load any notes from local database (SQLite)

        // Ensure db exists first. If not, create a new one.
        val db: SQLiteDatabase = requireActivity().openOrCreateDatabase("notesDb", MODE_PRIVATE, null)


        // Temp code for debugging only !!!
        var tempSql = "DROP TABLE IF EXISTS Notes"
        db.execSQL(tempSql)

        // Ensure there's a Notes table
        var sql = "CREATE TABLE IF NOT EXISTS Notes (Id INT, Title VARCHAR, Content VARCHAR);"
        db.execSQL(sql)

        // Count rows, check for existing data
        sql = "SELECT COUNT(*) FROM Notes;"
        var sqlResponse = db.rawQuery(sql, null )
        Log.d(ContentValues.TAG, "db columnCount: ${sqlResponse.columnCount}")
        Log.d(ContentValues.TAG, "db columnName: ${sqlResponse.getColumnName(0)}")
        sqlResponse.moveToFirst()
        Log.d(ContentValues.TAG, "db getInt(0): ${sqlResponse.getInt(0)}")
        var numRows = sqlResponse.getInt(0)
        Log.d(ContentValues.TAG, "db notes table row count: ${numRows}")

        // Fetch notes from database into an ArrayList
        var noteList = ArrayList<Note>()
        if(numRows>0) {
            sql="SELECT * FROM Notes;"
            sqlResponse = db.rawQuery(sql, null)
            sqlResponse.moveToFirst()
            var n = Note("","")
            for(i in 1..numRows) {
                n.title = sqlResponse.getString(1)
                n.content = sqlResponse.getString(2)
                Log.d(TAG, "db reading $i: ${n.title} | ${n.content.subSequence(0..10)}")
                noteList.add(n)
                sqlResponse.moveToNext()
                n = Note("","")
            }
            Log.d(TAG, "db - first 3 titles: ${noteList[0].title} | ${noteList[1].title} | ${noteList[2].title}")


        } else {    // if no rows, create mock data
            noteList = loadMockData()
        }

        // log data for debugging purposes
//        Log.d(TAG, "(frag1) db noteList count: ${noteList.count()}")
//        Log.d(TAG, "(frag1) db noteList(0): ${noteList[0].title} ${noteList[0].content.subSequence(0..5)}")
//        Log.d(TAG, "(frag1) db noteList(1): ${noteList[1].title} ${noteList[1].content.subSequence(0..5)}")
//        Log.d(TAG, "(frag1) db noteList(2): ${noteList[2].title} ${noteList[2].content.subSequence(0..5)}")

        //TODO: use Room and utilize it for caching strategy to reduce db calls for better performance

        return noteList
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun loadMockData():ArrayList<Note>{
        
        var mNoteList = ArrayList<Note>()

        // Make data
        mNoteList.add(Note(getString(R.string.mock1),  getString(R.string.mock1_content)))   // Resource strings used to facilitate global language translations.
        mNoteList.add(Note(getString(R.string.mock2), getString(R.string.mock2_content)))
        mNoteList.add(Note(getString(R.string.mock3), getString(R.string.mock3_content)))
        
        // Ensure db exists first. If not, create a new one.
        val db: SQLiteDatabase = requireActivity().openOrCreateDatabase("notesDb", MODE_PRIVATE, null)
        //TODO: refactor so that db opening is done in only one reusable place
        var sql = ""

        // insert mock data into database
        Log.d(TAG, "db adding mock data. ${mNoteList.count()} new records...")
        for(i in 0..<mNoteList.count()){
            Log.d(TAG, "insert sql: ${sql}")
            sql = "INSERT INTO Notes (Id, Title, Content) VALUES ($i,'${mNoteList[i].title}','${mNoteList[i].content}');"
            db.execSQL(sql)
        }

        // count rows
        sql = "SELECT COUNT (*) FROM Notes"
        var sqlResponse = db.rawQuery(sql, null)
        sqlResponse.moveToFirst()
        Log.d(ContentValues.TAG, "db mock notes created: ${sqlResponse.getInt(0)}")
        //TODO: refactor into a reusable function, or as part of a db helper object
        
        return mNoteList
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

