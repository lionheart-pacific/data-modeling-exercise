package com.lionheartpacific.practices.repository

data class WeighInLeaderboard(val entries: List<WeighInLeaderboardEntry>)

data class WeighInLeaderboardEntry(
    val actorId: Long,
    val weighIns: Int,
)
