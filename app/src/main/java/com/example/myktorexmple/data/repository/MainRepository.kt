package com.example.myktorexmple.data.repository

import com.example.myktorexmple.data.Model
import com.example.myktorexmple.data.network.ApiService
import com.example.myktorexmple.data.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiService: ApiService) {

    fun getPost() : Flow<Model> = flow {
        emit(apiService.getPost())
    }.flowOn(Dispatchers.IO)

}