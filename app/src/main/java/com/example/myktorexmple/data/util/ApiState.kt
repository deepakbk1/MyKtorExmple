package com.example.myktorexmple.data.util

import com.example.myktorexmple.data.Data

sealed class ApiState{
    object Loading : ApiState()
    object Empty : ApiState()
    class Success(val response: List<Data>) : ApiState()
    class Failure(val error:Throwable) : ApiState()
}
