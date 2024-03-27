package com.github.billman64.notes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
public class SharedViewModel: ViewModel() {

    private var _title = MutableLiveData<String>()   // LiveData for title of note
    private var _content = MutableLiveData<String>()   // LiveData for content of note

    // public getters
    @Inject
    var title: LiveData<String> = _title
        get() = _title

    @get:Inject
    val content: LiveData<String>
        get() = _content

    // public setters
    @Provides
    fun setTitle(newTitle: String){
        _title.value = newTitle
    }

    @Provides
    fun setContent(newContent: String){
        _content.value = newContent
    }








}