package com.lionheartpacific.practices.app

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SanityTest {
    @Test
    fun `one equals one`() {
        expectThat(1).isEqualTo(1)
    }
}
