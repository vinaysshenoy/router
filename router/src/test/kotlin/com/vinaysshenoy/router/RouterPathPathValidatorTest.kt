package com.vinaysshenoy.router

import org.amshove.kluent.shouldNotThrow
import org.amshove.kluent.shouldThrow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class RouterPathPathValidatorTest(
	private val path: String,
	private val valid: Boolean
) {

	companion object {
		@JvmStatic @Parameters
		fun testParams() = arrayOf<Any>(
				arrayOf("asd/123", true),
				arrayOf("asd", true),
				arrayOf(":asf", true),
				arrayOf(":asd/123", true),
				arrayOf("asd/:123/wer/:p2/:q2", true),
				arrayOf("asd/:as34/_45/.er_/:p1_q1./rty", true),
				arrayOf("asd/ 123/wer\n/:p2", false),
				arrayOf("asd/:p1?q1=123", false),
				arrayOf("asd/123/", false),
				arrayOf("/asd/:p1", false),
				arrayOf("/asd/:p1/", false),
				arrayOf("Asd/:p1", false),
				arrayOf("as*/:p1", false)
		)
	}

	private val routeCreator: (Map<String, String>) -> Route =
		{ _ -> throw NotImplementedError("Test") }

	@Test
	fun `test that instantiating RouterPath with the given route is validated properly`() {
		val function = { RouterPath(path, routeCreator) }

		if (valid) function shouldNotThrow IllegalArgumentException::class
		else function shouldThrow IllegalArgumentException::class
	}
}