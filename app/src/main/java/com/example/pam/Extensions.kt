package com.example.pam

fun String.format(s: String, vararg strings: String): String{
    var answer = ""
    for (string in strings){
        answer = s.replaceFirst("{}",string)
    }
    return answer
}