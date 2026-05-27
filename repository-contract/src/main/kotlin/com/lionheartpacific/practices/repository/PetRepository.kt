package com.lionheartpacific.practices.repository

interface Step1PetRepository {
    fun create(request: PetRequest, actorId: Long): Long
    fun updateWeight(id: Long, weight: Double, actorId: Long)
    fun findById(id: Long): Pet?
}

interface Step2PetRepository : Step1PetRepository {
    fun getWeightChart(id: Long): WeightChart?
}

interface Step3PetRepository : Step2PetRepository {
    fun getWeighInLeaderboard(window: kotlin.time.Duration): WeighInLeaderboard
}

interface Step4PetRepository : Step3PetRepository {
    fun updateName(id: Long, newName: String, actorId: Long)
    fun findPetIdsByAnyName(name: String): List<Long>
}
