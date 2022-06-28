package com.example.mynotesapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.remote.response.base.BaseResponse
import com.example.mynotesapp.repository.AccountRepository
import com.example.mynotesapp.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    var registerResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var onError: MutableLiveData<String?> = MutableLiveData()

    fun register(
        username: String,
        password: String
    ) = viewModelScope.launch {
        isLoading.value = true
        val request = accountRepository.register(username, password)

        when (request) {
            is NetworkResult.Success -> {
                isLoading.value = false
                registerResponse.value = request.data!!
            }
            is NetworkResult.Error -> {
                isLoading.value = false
                onError.value = request.message
            }
        }
    }
}