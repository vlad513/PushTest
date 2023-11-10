package com.example.pushtest

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pushtest.databinding.ActivityMainBinding
import com.example.pushtest.models.Message
import com.example.pushtest.models.NotificationsModel
import com.example.pushtest.models.TokenModel
import com.google.android.datatransport.runtime.TransportRuntime.initialize
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.technoac.thermomonitor.settingCriticalPoint.data.remote.RetrofitClient
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)



        binding.button.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        ContentValues.TAG,
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                binding.tv.text = task.result
            })
        }
        binding.button2.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveToken(
                    params = TokenModel(
                        id_users = "test12", token = binding.tv.text.toString()
                    )
                )
            }
        }

        binding.button3.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                push(
                    params = NotificationsModel(
                        id_users = "test12", message = Message(title = "Test message", body = binding.edText.text.toString())
                    )
                )
            }
        }
    }

    suspend fun saveToken(params: TokenModel): String? {
        val request = RetrofitClient.apiClient.postSaveToken(params = params)

        if (request.isSuccessful) {
            return request.body()!!
        }

        supervisorScope {
            async(Dispatchers.IO) {

            }
        }
        return null
    }

    suspend fun push(params: NotificationsModel): String? {
        val request = RetrofitClient.apiClient.postNotifications(params = params)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

}