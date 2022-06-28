package com.example.mynotesapp.data.remote.response.noteResponse.noteId


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("note_id")
    val noteId: String?
)