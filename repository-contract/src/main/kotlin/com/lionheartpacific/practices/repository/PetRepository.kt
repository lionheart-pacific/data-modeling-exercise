package com.lionheartpacific.practices.repository

interface PetRepository {
    fun save(pet: Pet): Pet
    fun findById(id: Long): Pet?
}
