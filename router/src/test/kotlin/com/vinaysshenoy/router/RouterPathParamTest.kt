package com.vinaysshenoy.router

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class RouterPathParamTest(
	private val path: String,
	private val expected: Set<PathParamIndex>
) {

	companion object {
		@JvmStatic @Parameters
		fun testParams() = arrayOf<Any>(
				arrayOf("asd/123", emptySet<PathParamIndex>()),
				arrayOf("asd", emptySet<PathParamIndex>()),
				arrayOf(":asf", setOf(PathParamIndex("asf", 0))),
				arrayOf(":asd/123", setOf(PathParamIndex("asd", 0))),
				arrayOf("asd/:123/wer/:p2/:q2", setOf(PathParamIndex("123", 1), PathParamIndex("p2", 3), PathParamIndex("q2", 4))),
				arrayOf("asd/:as34/_45/.er_/:p1_q1./rty", setOf(PathParamIndex("as34", 1), PathParamIndex("p1_q1.", 4)))
		)
	}

	private val routeCreator: (Map<String, String>) -> Route =
		{ _ -> throw NotImplementedError("Test") }

	@Test
	fun `test that instantiating RouterPath with the given route generates path params properly`() {
		val routerPath = RouterPath(path, routeCreator)
		routerPath.pathParams shouldEqual expected
	}
}