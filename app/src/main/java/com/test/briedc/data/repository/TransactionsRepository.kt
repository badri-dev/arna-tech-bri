package com.test.briedc.data.repository

import com.google.gson.JsonObject
import com.test.briedc.data.models.HistoryModel
import com.test.briedc.data.models.SaleModel
import com.test.briedc.data.models.SettlementModel
import com.test.briedc.data.remote.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsRepository(private val api: ApiServices) {

    suspend fun getTransactionsHistory(): Result<List<HistoryModel>> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.getTransactionHistory()
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun postSale(body: JsonObject): Result<SaleModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.postSale(body)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun postSettlement(body: JsonObject): Result<SettlementModel> =
        try {
            val resp = withContext(Dispatchers.IO) {
                api.postSettlement(body)
            }
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
}