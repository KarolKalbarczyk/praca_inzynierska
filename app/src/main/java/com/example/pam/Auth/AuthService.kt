package com.example.pam.Auth

interface IAuthService{
    fun signin(login: String, password: String)

    fun logout()

    fun isSignedin():Boolean
}

class AuthService : IAuthService {

    override fun signin(login: String, password: String){
        if (login == "admin" && password == "admin"){
            AuthState.isSignedIn = true
        }
    }

    override fun logout(){
        AuthState.isSignedIn = false
    }

    override fun isSignedin():Boolean = AuthState.isSignedIn
}

object AuthState{
    var isSignedIn = false
}