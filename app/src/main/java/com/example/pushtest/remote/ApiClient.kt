package com.technoac.thermomonitor.settingCriticalPoint.data.remote

import com.example.pushtest.models.NotificationsModel
import com.example.pushtest.models.TokenModel
import retrofit2.Response

class ApiClient(
    private val apiService: ApiService
) {
    suspend fun postSaveToken(params:TokenModel): Response<String> {
        return apiService.saveToken(params = params)
    }

    suspend fun postNotifications(params:NotificationsModel): Response<String> {
        return apiService.postNotification(params = params)
    }
}