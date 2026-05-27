package com.lionheartpacific.practices.repository

import kotlin.time.Clock
import kotlin.time.Instant

interface TestClock : Clock {
    fun setNow(value: Instant)
}