package com.example.mynotesapp.data.remote.response.noteResponse.noteId


import com.google.gson.annotations.SerializedName

data class NoteIdResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)