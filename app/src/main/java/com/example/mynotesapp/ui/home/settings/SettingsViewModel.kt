package com.example.mynotesapp.ui.home.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.remote.response.base.BaseResponse
import com.example.mynotesapp.repository.NoteRepository
import com.example.mynotesapp.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 28.06.2022.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(private val noteRepository: NoteRepository): ViewModel() {
    var deleteAccountResponse : MutableLiveData<BaseResponse> = MutableLiveData()
    fun deleteAccount(
        username :String
    ) = viewModelScope.launch {
        val request = noteRepository.deleteAccount(username)

        when(request){
            is NetworkResult.Success ->{
                deleteAccountResponse.value = request.data!!
            }
        }
    }
}