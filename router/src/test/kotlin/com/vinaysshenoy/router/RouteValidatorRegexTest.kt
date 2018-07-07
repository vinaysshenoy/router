package com.vinaysshenoy.router

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class RouteValidatorRegexTest(
	private val route: String,
	private val valid: Boolean
) {

	companion object {
		@JvmStatic @Parameters
		fun testParams() = arrayOf<Any>(
				arrayOf("asd/123", true),
				arrayOf("asd/123?q1a=31", true),
				arrayOf("asd/123?q1=3&q2=3a", true),
				arrayOf("asd/123?q1=3&q2=3a&q3=&q4=asd_456", true),
				arrayOf("asd", true),
				arrayOf("asd/123", true),
				arrayOf("asd/123/wer/p2/q2", true),
				arrayOf("asd/as34/_45/.er_/p1_q1./rty", true)
		)
	}

	@Test
	fun `test that the valid route regex matches as expected`() {

		validRouteRegex.matches(route) shouldEqual valid
	}
}