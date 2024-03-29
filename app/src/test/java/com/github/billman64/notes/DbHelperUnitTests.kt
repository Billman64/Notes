package com.github.billman64.notes

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.github.billman64.notes.Model.DbHelper
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class DbHelperUnitTests {

    @Mock
    private lateinit var c: Context   // Mock context

    private lateinit var dbHelper: DbHelper

    @Before
    fun setup(){
        val c = Mockito.mock(Context::class.java)
        MockitoAnnotations.openMocks(this)        //TODO: fix DbHelper tests with context issue

        Mockito.`when`(c.openOrCreateDatabase("notesDb", Context.MODE_PRIVATE, null))
            .thenReturn(Mockito.mock(SQLiteDatabase::class.java))
        //        val c:Context = mock(Context::class.java) // alternate context

        mockkStatic(Log::class)
        every { Log.d(any(), any())} returns 0
        every { Log.e(any(), any())} returns 0


        dbHelper = DbHelper(c)

    }

    @Test
    fun countRowsTest() {
//        dbHelper.newRecord("Some title", "Some content")
//        dbHelper.readRecord(1)
//        assertEquals(1, dbHelper.countRows())   //TODO: figure out why 0 is returned instead of 1
    }

    @Test
    fun newRecord() {
        assertEquals(dbHelper.newRecord("a", "b"), 1)
    }

    @Test
    fun deleteRecord(){
//        dbHelper.newRecord("dummy record", "to be deleted.")
//        assert(dbHelper.deleteRecord(1) == 1)   //TODO: figure out why rawQuery is null
    }

    @Test
    fun contiguousIdNumbers() {
        //TODO: Test to see if all id's in database table are contiguous, with no gaps from deletions
    }
}