package com.example.compose

import org.junit.Test
import java.text.NumberFormat
import org.junit.Assert.assertEquals


class TipTimeTest {

    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val amount = 10.00
        val tipPercent = 20.00
        val result = 2.0
        assertEquals(result, calculateTip(amount, tipPercent, false),10.0)
    }

}