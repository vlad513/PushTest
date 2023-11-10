package com.technoac.thermomonitor.settingCriticalPoint.data.remote

import com.example.pushtest.models.NotificationsModel
import com.example.pushtest.models.TokenModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/savetoken")
    suspend fun saveToken(
        @Body params: TokenModel
    ): Response<String>

    @POST("/notification")
    suspend fun postNotification(
        @Body params: NotificationsModel
    ):Response<String>
}