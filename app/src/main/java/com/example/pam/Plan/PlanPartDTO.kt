package com.example.pam.Plan

data class PlanPartDTO(
    val name: String,
    val photo: Int,
    val latitude: Double,
    val longitude: Double,
    val id: Int,
    val order: Int,
    var done: Boolean,
    val antiqueId: Int
){
    var distance: Int? = null
}
