package com.github.billman64.notes.Model

data class Note(var title:String, var content:String){

    var mTitle:String
        internal set
    var mContent:String
        internal set

    init {
        mTitle = title
        mContent = content
    }

}
