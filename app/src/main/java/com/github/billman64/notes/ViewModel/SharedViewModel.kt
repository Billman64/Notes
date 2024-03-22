package com.github.billman64.notes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class SharedViewModel: ViewModel() {

    private var _title = MutableLiveData<String>()   // LiveData for title of note
    private var _content = MutableLiveData<String>()   // LiveData for content of note

    // public getters
    var title: LiveData<String> = _title
        get() = _title
    val content: LiveData<String>
        get() = _content

    // public setters
    fun setTitle(newTitle: String){
        _title.value = newTitle
    }
    fun setContent(newContent: String){
        _content.value = newContent
    }








}