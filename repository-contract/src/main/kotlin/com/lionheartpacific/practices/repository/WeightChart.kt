package com.lionheartpacific.practices.repository

import kotlin.time.Instant

data class WeightChart(val entries: List<WeightEntry>)

data class WeightEntry(
    val recordedAt: Instant,
    val weight: Double,
)
