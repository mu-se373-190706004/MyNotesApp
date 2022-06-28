package com.example.mynotesapp.utils.extension

import com.example.mynotesapp.data.remote.response.noteResponse.Note


interface OnItemClickListener {
    fun onClick(note : Note)
}