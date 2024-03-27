package com.github.billman64.notes.Model

class Utilities {

    fun cutText(text:String, threshold:Int = 10):String{
        var output:String

        if(text.length>=threshold) output = text.subSequence(0..threshold).toString()
        else {
            output = text.subSequence(0..<text.count()).toString()
        }

        return output
    }
}