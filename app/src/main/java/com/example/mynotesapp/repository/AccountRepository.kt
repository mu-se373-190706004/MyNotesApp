package com.example.mynotesapp.repository

import com.example.mynotesapp.network.service.ApiService
import com.example.mynotesapp.utils.base.BaseRepository
import javax.inject.Inject

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
class AccountRepository @Inject constructor(private val apiService: ApiService ) : BaseRepository() {

    suspend fun login(
        username: String,
        password : String
    ) = safeApiRequest {
        apiService.login(username,password)
    }

    suspend fun register(
        username: String,
        password: String
    ) = safeApiRequest {
        apiService.register(username,password)
    }
}