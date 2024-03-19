package com.github.billman64.notes.Model

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.ContextCompat.getString
import com.github.billman64.notes.R
import com.google.android.material.internal.ContextUtils.getActivity

class DbHelper(context: Context): SQLiteOpenHelper(context, DBNAME,null, 1) {

    companion object {
        private const val DBNAME = "notesDb"
    }

    private val TAG = this.javaClass.simpleName
    private val DBNAME = "notesDb"
    private val TABLENAME = "Notes"
    public fun loadData(context: Context): ArrayList<Note> {

        //TODO: create a DB helper object to separate and centralize database operations.

        // Load any notes from local database (SQLite)

        // Ensure db exists first. If not, create a new one.
//        val db: SQLiteDatabase = Activity().openOrCreateDatabase(DBNAME,
//            Context.MODE_PRIVATE, null)

//        val db: SQLiteDatabase = requireActivity().openOrCreateDatabase("notesDb",
//            MODE_PRIVATE, null)   //TODO: Find out why this code would work inside a fragment, but not here. Must be a context issue.

        val db: SQLiteDatabase = context.openOrCreateDatabase(DBNAME,
            MODE_PRIVATE, null) // does not work

//        val db:SQLiteDatabase = readableDatabase


//        // Temp code for debugging only !!!
//        var tempSql = "DROP TABLE IF EXISTS ${TABLENAME}"
//        db.execSQL(tempSql)

        // Ensure there's a Notes table
        var sql = "CREATE TABLE IF NOT EXISTS ${TABLENAME} (Id INT, Title VARCHAR, Content VARCHAR);"
        db.execSQL(sql)

        // Count rows, check for existing data
//        sql = "SELECT COUNT(*) FROM ${TABLENAME};"
//        var sqlResponse = db.rawQuery(sql, null )
//        Log.d(ContentValues.TAG, "db columnCount: ${sqlResponse.columnCount}")
//        Log.d(ContentValues.TAG, "db columnName: ${sqlResponse.getColumnName(0)}")
//        sqlResponse.moveToFirst()
        
        var numRows = countRows(context)
        Log.d(ContentValues.TAG, "db getInt(0): ${numRows}")
        
        Log.d(ContentValues.TAG, "db notes table row count: ${countRows(context)}")

        // Fetch notes from database into an ArrayList
        var noteList = ArrayList<Note>()
        if(numRows>0) {
            sql="SELECT * FROM ${TABLENAME};"
            var sqlResponse = db.rawQuery(sql, null)
            sqlResponse.moveToFirst()
            var currentNote = Note("","")
            for(i in 1..numRows) {
                currentNote.title = sqlResponse.getString(1)
                currentNote.content = sqlResponse.getString(2)
                Log.d(TAG, "db reading $i: ${currentNote.title} | ${currentNote.content.subSequence(0..10)}")
                noteList.add(currentNote)
                sqlResponse.moveToNext()
                currentNote = Note("","")
            }
            Log.d(TAG, "db - first 3 titles: ${noteList[0].title} | ${noteList[1].title} | ${noteList[2].title}")


        } else {    // if no rows, create mock data
            noteList = loadMockData(context)
        }

        // log data for debugging purposes
//        Log.d(TAG, "(frag1) db noteList count: ${noteList.count()}")
//        Log.d(TAG, "(frag1) db noteList(0): ${noteList[0].title} ${noteList[0].content.subSequence(0..5)}")
//        Log.d(TAG, "(frag1) db noteList(1): ${noteList[1].title} ${noteList[1].content.subSequence(0..5)}")
//        Log.d(TAG, "(frag1) db noteList(2): ${noteList[2].title} ${noteList[2].content.subSequence(0..5)}")

        //TODO: use Room and utilize it for caching strategy to reduce db calls for better performance

        db.close()
        return noteList
    }

    private fun loadMockData(context: Context):ArrayList<Note>{

        var mNoteList = ArrayList<Note>()

        // Mock data
        // Resource strings used to facilitate global language translations.
        mNoteList.add(Note(getString(Activity(), R.string.mock1),  getString(Activity(), R.string.mock1_content)))
        mNoteList.add(Note(getString(Activity(), R.string.mock2), getString(Activity(), R.string.mock2_content)))
        mNoteList.add(Note(getString(Activity(), R.string.mock3), getString(Activity(), R.string.mock3_content)))

        // Ensure db exists first. If not, create a new one.
        val db: SQLiteDatabase = Activity().openOrCreateDatabase(DBNAME,
            Context.MODE_PRIVATE, null)
        //TODO: refactor so that db opening is done in only one reusable place
        var sql = ""

        // insert mock data into database
        Log.d(TAG, "db adding mock data. ${mNoteList.count()} new records...")
        for(i in 0..<mNoteList.count()){
            Log.d(TAG, "insert sql: ${sql}")
            sql = "INSERT INTO ${TABLENAME} (Id, Title, Content) VALUES ($i,'${mNoteList[i].title}','${mNoteList[i].content}');"
            db.execSQL(sql)
        }


        Log.d(ContentValues.TAG, "db mock notes created: ${countRows(context)}")
        db.close()

        return mNoteList
    }
    
    private fun countRows(context: Context):Int{
        var numRows = 0

        val mDb: SQLiteDatabase = context.openOrCreateDatabase(DBNAME,
            Context.MODE_PRIVATE, null)
        
        // count rows
        var mSql = "SELECT COUNT (*) FROM ${TABLENAME}"
        var mSqlResponse = mDb.rawQuery(mSql, null)
        mSqlResponse.moveToFirst()
        numRows = mSqlResponse.getInt(0)
        mDb.close()
                
        return numRows
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")

            val p0 = Activity().openOrCreateDatabase(DBNAME,
            MODE_PRIVATE, null)   //TODO: Find out why this code would work inside a fragment, but not here. Must be a context issue.

//        val db: SQLiteDatabase = Activity().openOrCreateDatabase("notesDb",
//            MODE_PRIVATE, null) // does not work

//        val db:SQLiteDatabase = readableDatabase
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}