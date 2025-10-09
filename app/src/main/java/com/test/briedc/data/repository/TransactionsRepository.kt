package com.test.briedc.data.repository

import com.google.gson.JsonObject
import com.test.briedc.data.models.HistoryModel
import com.test.briedc.data.models.SaleModel
import com.test.briedc.data.models.SettlementModel
import com.test.briedc.data.remote.ApiServices
import com.test.briedc.utils.AppPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsRepository(private val api: ApiServices) {

    suspend fun getTransactionsHistory(token: String): Result<List<HistoryModel>> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.getTransactionHistory(token)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun postSale(token: String, body: JsonObject): Result<SaleModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.postSale(body, token)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun postSettlement(token: String, body: JsonObject): Result<SettlementModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.postSettlement(body, token)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
}