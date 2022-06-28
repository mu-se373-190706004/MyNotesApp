package com.example.mynotesapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.remote.response.base.BaseResponse
import com.example.mynotesapp.data.remote.response.noteResponse.NoteResponse
import com.example.mynotesapp.repository.NoteRepository
import com.example.mynotesapp.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    var noteListResponse : MutableLiveData<NoteResponse> = MutableLiveData()
    var deleteNoteResponse : MutableLiveData<BaseResponse> = MutableLiveData()

    var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    var onError : MutableLiveData<String?> = MutableLiveData()

    fun fetchAllNotes(
        username : String
    ) = viewModelScope.launch {
        isLoading.value = true
        val request = noteRepository.fetchAllNotes(username)

        when(request){
            is NetworkResult.Success ->{
                isLoading.value = false
                noteListResponse.value = request.data!!
            }
            is NetworkResult.Error ->{
                isLoading.value = false
                onError.value = request.message
            }
        }
    }

    fun deleteNote(
        noteId : String
    ) = viewModelScope.launch {
        val request = noteRepository.deleteNote(noteId)
        when(request){
            is NetworkResult.Success ->{
                deleteNoteResponse.value = request.data!!
            }
        }
    }
}