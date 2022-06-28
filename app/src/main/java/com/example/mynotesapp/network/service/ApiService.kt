package com.example.mynotesapp.network.service

import com.example.mynotesapp.data.remote.response.base.BaseResponse
import com.example.mynotesapp.data.remote.response.noteResponse.NoteResponse
import com.example.mynotesapp.data.remote.response.noteResponse.noteId.NoteIdResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
interface ApiService {

    @FormUrlEncoded
    @POST("/MyNote/login.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse

    @FormUrlEncoded
    @POST("/MyNote/register.php")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse

    @FormUrlEncoded
    @POST("/MyNote/notesfunction/get_notes.php")
    suspend fun fetchAllNotes(
        @Field("username") username: String
    ): NoteResponse

    @FormUrlEncoded
    @POST("/MyNote/notesfunction/add_note_id.php")
    suspend fun createNoteId(
        @Field("username") username: String,
        @Field("comment") comment: String,
    ): NoteIdResponse

    @FormUrlEncoded
    @POST("/MyNote/notesfunction/add_note.php")
    suspend fun createNewNote(
        @Field("username") username: String,
        @Field("note_id") noteId: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("priority") priority: String,
        @Field("state") state: String,
    ): BaseResponse

    @FormUrlEncoded
    @POST("/MyNote/notesfunction/delete_note.php")
    suspend fun deleteNote(
        @Field("note_id")noteId : String
    ) : BaseResponse

    @FormUrlEncoded
    @POST("/MyNote/delete.php")
    suspend fun deleteAccount(
        @Field("username")username : String
    ) : BaseResponse

}