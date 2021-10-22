package com.example.myktorexmple.data.network

import com.example.myktorexmple.data.Data
import com.example.myktorexmple.data.Model
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import javax.inject.Inject

class ApiService @Inject constructor() {

    private val client = HttpClient(Android) {
        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }

    suspend fun getPost(): Model {
        return client.get {
            url("https://reqres.in/api/users")
        }
    }
}