package com.example.mynotesapp.ui.home.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.local.model.Priority
import com.example.mynotesapp.data.remote.response.base.BaseResponse
import com.example.mynotesapp.data.remote.response.noteResponse.noteId.NoteIdResponse
import com.example.mynotesapp.repository.NoteRepository
import com.example.mynotesapp.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
@HiltViewModel
class AddNoteViewModel @Inject constructor(private val noteRepository: NoteRepository): ViewModel() {
    var addNoteIdResponse : MutableLiveData<NoteIdResponse> = MutableLiveData()
    var addNewNoteResponse : MutableLiveData<BaseResponse> = MutableLiveData()

    var onError : MutableLiveData<String?> = MutableLiveData()

    fun createNoteId(
        username :String,
        comment : String
    ) = viewModelScope.launch {
        val request = noteRepository.createNoteId(username,comment)
        when(request){
            is NetworkResult.Success ->{
                addNoteIdResponse.value = request.data!!
            }
        }
    }

    fun createNewNote(
        username: String,
        noteId: String,
        title: String,
        description: String,
        priority: String,
        state: String,
    ) = viewModelScope.launch {

        val request = noteRepository.createNewNote(username,noteId,title,description,priority,state)
        when(request){
            is NetworkResult.Success ->{
                addNewNoteResponse.value = request.data!!
            }
            is NetworkResult.Error ->{
                onError.value = request.message
            }
        }
    }
}