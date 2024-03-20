package com.github.billman64.notes.Model

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
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
    private val c = context
    public fun loadData(): ArrayList<Note> {
        // Load any notes from local database (SQLite)

        // Ensure db exists first. If not, create a new one.
        val db: SQLiteDatabase = openDb()

//        val db:SQLiteDatabase = readableDatabase  // alternative


        // Temp code for debugging only !!!
//        var tempSql = "DROP TABLE IF EXISTS ${TABLENAME}"
//        db.execSQL(tempSql)

        // Ensure there's a Notes table
        var sql = "CREATE TABLE IF NOT EXISTS ${TABLENAME} (Id INT, Title VARCHAR, Content VARCHAR);"
        db.execSQL(sql)

        var numRows = countRows()
        Log.d(ContentValues.TAG, "db getInt(0): ${numRows}")
        
        Log.d(ContentValues.TAG, "db notes table row count: ${countRows()}")

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
            noteList = loadMockData()
        }

        logDataSample(noteList)



        //TODO: use Room and utilize it for caching strategy to reduce db calls for better performance

        db.close()
        return noteList
    }
    private fun logDataSample(noteList:ArrayList<Note>) {
        // log data for debugging purposes
        val MAX = 3
        val LENGTH = 10

        // log count
        Log.d(TAG, "db noteList count: ${noteList.count()}")

        // log 3 records, at most
        var i = 0
        while(i<noteList.count() && i<MAX) {
           Log.d(TAG, " db noteList(0): ${noteList[0].title} ${noteList[0].content.subSequence(0..LENGTH)}")
            i++
        }
    }
    private fun loadMockData():ArrayList<Note>{

        var mNoteList = ArrayList<Note>()

        // Mock data
        // Resource strings used to facilitate global language translations.
        mNoteList.add(Note(getString(c, R.string.mock1),  getString(c, R.string.mock1_content)))
        mNoteList.add(Note(getString(c, R.string.mock2), getString(c, R.string.mock2_content)))
        mNoteList.add(Note(getString(c, R.string.mock3), getString(c, R.string.mock3_content)))

        // Ensure db exists first. If not, create a new one.
//        val db: SQLiteDatabase = Activity().openOrCreateDatabase(DBNAME,
//            Context.MODE_PRIVATE, null)

        val db: SQLiteDatabase = openDb()

        var sql = ""

        // insert the mock data into database
        Log.d(TAG, "db adding mock data. ${mNoteList.count()} new records...")
        for(i in 0..<mNoteList.count()){
            Log.d(TAG, "insert sql: ${sql}")
            sql = "INSERT INTO ${TABLENAME} (Id, Title, Content) VALUES ($i,'${mNoteList[i].title}','${mNoteList[i].content}');"
            db.execSQL(sql)
        }


        Log.d(ContentValues.TAG, "db mock notes created: ${countRows()}")
        db.close()

        return mNoteList
    }
    
    fun countRows():Int{
        var numRows = 0

        val mDb: SQLiteDatabase = openDb()
        
        // count rows
        var mSql = "SELECT COUNT (*) FROM ${TABLENAME}"
        var mSqlResponse = mDb.rawQuery(mSql, null)
        mSqlResponse.moveToFirst()
        numRows = mSqlResponse.getInt(0)
        mDb.close()
                
        return numRows
    }

    fun newRecord(title:String, content:String): Int {

        // Find highest Id # in db table
        val mDb = openDb()
        var sql = "SELECT MAX(Id) FROM $TABLENAME;"
        var response = mDb.rawQuery(sql, null)
        response.moveToFirst()
//        Log.d(TAG, " newRecord() - response value: ${response.getInt(0)}")
        var i = response.getInt(0)+1

        // Insert new row of the given data
        Log.d(TAG, "Inserting new record. id#: $i title: $title")
        sql = "INSERT INTO ${TABLENAME} (Id, Title, Content) VALUES ('$i','$title','$content');"
        mDb.execSQL(sql)

        mDb.close()
        return 1
    }

    fun readRecord(id:Int): Note {

        val mDb = openDb()
        val sql = "SELECT Title, Content FROM $TABLENAME WHERE Id=$id;"
        Log.d(TAG, "readRecord() SQL: $sql")
        lateinit var response: Cursor

        // Fetch record based on given id
        try {
            response = mDb.rawQuery(sql, null)
            response.moveToFirst()
        } catch(e:Exception) {      // Handling for any database error
            Log.e(TAG, "readRecord() error. Message: ${e.message}")
        }

        // If the record is found, store it in a local Note
        var note = Note("","")
        if(response.count>0) {
            note = Note(response.getString(0), response.getString(1))
            Log.d(TAG, "readRecord() - ${note.title} | ${note.content}")
        } else Log.d(TAG, "readRecord() - record not found (id#: $id)")     // Handling for non-existent record

        mDb.close()
        return note
    }

    fun deleteRecord(id:Int):Int {

        val mDb = openDb()
        var sql = "DELETE FROM $TABLENAME WHERE Id = '$id';"
        var response = mDb.rawQuery(sql, null)
        Log.d(TAG, "Record deleted. Id#: $id")

        //TODO: Maintain contiguous id numbering by adjusting records of higher id #'s to move down by 1

        mDb.close()
        return 1
    }

    fun updateRecord(id:Int, title:String, content:String):Int{
        // Updates record with given parameters
        // TODO: make non-id parameters optional as a safeguard against accidentally deleting data

        val mDb = openDb()
        var sql = "UPDATE $TABLENAME SET Title = '$title', Content = '$content' WHERE Id=$id;"
        var response = mDb.rawQuery(sql, null).moveToFirst()
        Log.d(TAG, "Record updated Id#: $id Title: $title")

        mDb.close()
        return 1
    }

    private fun openDb():SQLiteDatabase {
        val mDb: SQLiteDatabase = c.openOrCreateDatabase(DBNAME,
            Context.MODE_PRIVATE, null)
        return mDb
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")

        Log.d(TAG, "onCreate()")

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