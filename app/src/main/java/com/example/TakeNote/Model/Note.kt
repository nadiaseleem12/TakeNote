package com.example.TakeNote.Model


data class Note(

    var id: Int = 0,
    var title: String,
    var details: String,
    var date: String,
    var isArchived:Boolean = false
)

val notesList = mutableListOf<Note>()