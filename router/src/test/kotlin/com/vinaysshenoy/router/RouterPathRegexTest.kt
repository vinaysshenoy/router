package com.vinaysshenoy.router

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class RouterPathRegexTest(
	private val path: String,
	private val expected: String
) {

	companion object {
		@JvmStatic @Parameters
		fun testParams() = arrayOf<Any>(
				arrayOf("asd/123", "asd/123"),
				arrayOf("asd", "asd"),
				arrayOf(":asf", "\\w+"),
				arrayOf(":asd/123", "\\w+/123"),
				arrayOf("asd/:123/wer/:p2/:q2", "asd/\\w+/wer/\\w+/\\w+"),
				arrayOf("asd/:as34/_45/.er_/:p1_q1./rty", "asd/\\w+/_45/.er_/\\w+/rty")
		)
	}

	private val routeCreator: (Map<String, String>) -> Route =
		{ _ -> throw NotImplementedError("Test") }

	@Test
	fun `test that instantiating RouterPath with the given route generates the route path regex properly`() {
		val routerPath = RouterPath(path, routeCreator)
		routerPath.routeRegex.pattern shouldEqual expected
	}
}