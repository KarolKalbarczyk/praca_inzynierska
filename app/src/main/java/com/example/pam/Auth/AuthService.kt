package com.example.pam.Auth

import android.content.res.Resources
import com.example.pam.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

interface IAuthService{
    fun signin(login: String, password: String)

    fun logout()

    fun isSignedin():Boolean
}

class AuthService(private val res: Resources) : IAuthService {

    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

    override fun signin(login: String, password: String){
        val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.MINUTES).build();

        val json = JSONObject()
        json.put("login", login)
        json.put("password", password)
        val body: RequestBody = json.toString().toRequestBody(JSON)

        val request: Request = Request.Builder()
            .url(res.getString(R.string.authServer) + "/signin")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody  = response.body?.string()
            if (responseBody.isNullOrBlank()){
                AuthState.isSignedIn = false
            }else{
                AuthState.isSignedIn = true
                AuthState.token = responseBody
            }

        }
    }

    override fun logout(){
        AuthState.isSignedIn = false
        AuthState.token = null
        val request: Request = Request.Builder()
            .url(res.getString(R.string.authServer) + "/signout?token=" + AuthState.token)
            .build()

        val client = OkHttpClient.Builder().connectTimeout(100, TimeUnit.MINUTES).build();
        client.newCall(request).execute().use {  }
    }

    override fun isSignedin():Boolean = AuthState.isSignedIn
}

object AuthState{
    var isSignedIn = false
    var token: String? = null
}