package com.example.mynotesapp.data.remote.response.noteResponse


import com.google.gson.annotations.SerializedName

data class NoteResponse(
    @SerializedName("notes")
    val notes: List<Note>?,
    @SerializedName("success")
    val success: Int?
)