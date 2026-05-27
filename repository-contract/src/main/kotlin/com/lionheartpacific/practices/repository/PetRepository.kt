package com.lionheartpacific.practices.repository

interface PetRepository {
    fun create(request: PetRequest): Long
    fun updateWeight(id: Long, weight: Double)
    fun findById(id: Long): Pet?
}
