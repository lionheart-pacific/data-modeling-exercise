package com.lionheartpacific.practices.repository

data class Pet(
    val id: Long,
    val name: String,
    val weight: Double,
    val status: PetStatus,
)
