package com.example.mynotesapp.data.remote.response.base


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)