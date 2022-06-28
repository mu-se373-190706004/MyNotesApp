package com.example.mynotesapp.repository

import com.example.mynotesapp.network.service.ApiService
import com.example.mynotesapp.utils.base.BaseRepository
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
class NoteRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun fetchAllNotes(
        username : String
    ) = safeApiRequest {
        apiService.fetchAllNotes(username)
    }

    suspend fun createNoteId(
        username: String,
        comment : String
    ) = safeApiRequest {
        apiService.createNoteId(username,comment)
    }

    suspend fun createNewNote(
        username: String,
        noteId: String,
        title: String,
        description: String,
        priority: String,
        state: String,
    ) = safeApiRequest {
        apiService.createNewNote(username,noteId,title,description,priority,state)
    }

    suspend fun deleteNote(
        noteId: String
    ) = safeApiRequest {
        apiService.deleteNote(noteId)
    }

    suspend fun deleteAccount(
        username : String
    ) = safeApiRequest {
        apiService.deleteAccount(username)
    }
}