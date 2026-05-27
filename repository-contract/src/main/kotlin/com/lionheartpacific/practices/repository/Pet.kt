package com.lionheartpacific.practices.repository

data class Pet(
    val id: Long,
    val name: String,
    val status: PetStatus,
    val weight: Double?,
)
