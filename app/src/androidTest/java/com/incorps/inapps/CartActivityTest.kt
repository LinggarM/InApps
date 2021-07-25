package com.incorps.inapps

import androidx.test.core.app.ActivityScenario
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CartActivityTest {

    private lateinit var scenario: ActivityScenario<CartActivity>

    @Before
    fun setUp() {
    }

    @Test
    fun testLaunch() {
        scenario = ActivityScenario.launch(CartActivity::class.java)
    }

    @After
    fun tearDown() {
    }
}