package com.github.billman64.notes

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.billman64.notes.Model.DbHelper
import com.github.billman64.notes.Model.Utilities
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * A local unit test that tests functions in the Utilities class..
 */

class UtilitiesTests {
    @Test
    fun cutText() {
        val u: Utilities = Utilities()
        assertEquals(u.cutText("abcdefg", 3),"abc")
    }

    @Test
    fun cutTextLongerThanText() {
        val u: Utilities = Utilities()
        assertEquals(u.cutText("abcdefg", 20),"abcdefg")
    }

    @Test
    fun cutTextZero() {
        val u: Utilities = Utilities()
        assertEquals(u.cutText("abcdefg", 0),"")
    }
}