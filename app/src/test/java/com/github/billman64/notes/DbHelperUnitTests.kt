package com.github.billman64.notes

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.billman64.notes.Model.DbHelper
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
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
//        c = Mockito.mock(Context::class.java)
//        MockitoAnnotations.openMocks(this)        //TODO: fix DbHelper tests with context issue
//
//        Mockito.`when`(c.openOrCreateDatabase("notesDb", Context.MODE_PRIVATE, null))
//            .thenReturn(Mockito.mock(SQLiteDatabase::class.java))
//        dbHelper = DbHelper(c)
    }

    @Test
    fun countRowsTest() {
//        dbHelper.newRecord("Some title", "Some content")
//        assertEquals(dbHelper.countRows(), dbHelper.countRows() > 0)
    }
}