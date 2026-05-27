package com.lionheartpacific.practices.repository

interface Step1PetRepository {
    fun create(request: PetRequest, actor: Actor): Long
    fun updateWeight(id: Long, weight: Double, actor: Actor)
    fun findById(id: Long): Pet?
}

interface Step2PetRepository : Step1PetRepository {
    fun getWeightChart(id: Long): WeightChart?
}