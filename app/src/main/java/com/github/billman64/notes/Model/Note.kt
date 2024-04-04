package com.github.billman64.notes.Model

import dagger.Module

@Module
data class Note(var id:Int=0, var title:String, var content:String){

    var mId:Int
        internal set
    var mTitle:String
        internal set
    var mContent:String
        internal set

    init {
        mId = id
        mTitle = title
        mContent = content
    }

}
