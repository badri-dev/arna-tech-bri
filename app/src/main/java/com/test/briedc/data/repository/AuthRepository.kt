package com.test.briedc.data.repository

import com.google.gson.JsonObject
import com.test.briedc.data.models.LoginModel
import com.test.briedc.data.models.RegisterModel
import com.test.briedc.data.remote.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(val api: ApiServices) {

    suspend fun login(body: JsonObject): Result<LoginModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.login(body)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun register(body: JsonObject): Result<RegisterModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.register(body)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
}