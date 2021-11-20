package com.example.pam.Auth

import androidx.lifecycle.ViewModel

class SigninViewModel : ViewModel() {
    private lateinit var authService: IAuthService

    var name: String? = null

    fun isLoggedIn() = this.authService.isSignedin()

    fun init(authService: IAuthService){
        this.authService = authService
    }

    fun signin(name: String, password: String, callback: () -> (Unit)){
        Thread {
            authService.signin(name, password)
            callback()
        }.start()
    }

    fun logout(){
        authService.logout()
    }
}