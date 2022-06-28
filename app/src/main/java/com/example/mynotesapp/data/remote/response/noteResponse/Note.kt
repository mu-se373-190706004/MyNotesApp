package com.example.mynotesapp.data.remote.response.noteResponse


import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("description")
    val description: String?,
    @SerializedName("note_date")
    val noteDate: String?,
    @SerializedName("note_id")
    val nodeId: String?,
    @SerializedName("priority")
    val priority: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("title")
    val title: String?
)