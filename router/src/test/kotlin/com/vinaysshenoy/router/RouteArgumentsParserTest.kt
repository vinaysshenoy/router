package com.vinaysshenoy.router

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class RouteArgumentsParserTest {

	companion object {
		@JvmStatic @Parameters
		fun testParams() = arrayOf<Any>(
				arrayOf("asd/123" to emptySet<Set<PathParamIndex>>(), emptyMap<String, String>()),
				arrayOf(
						"asd/w1/qwe/w2" to setOf(PathParamIndex("param1", 1), PathParamIndex("param2", 3)),
						mapOf("param1" to "w1", "param2" to "w2")
				),
				arrayOf(
						"asd/32/tyu" to setOf(PathParamIndex("p2", 1), PathParamIndex("p1", 0)),
						mapOf("p1" to "asd", "p2" to "32")
				),
				arrayOf(
						"asd/32/tyu?" to setOf(PathParamIndex("p1", 1)), mapOf("p1" to "32")
				),
				arrayOf(
						"asd/32/tyu?q1=45&q2=456" to setOf(PathParamIndex("p1", 1)),
						mapOf("p1" to "32", "q1" to "45", "q2" to "456")
				),
				arrayOf(
						"asd/32/tyu?q1=45&p1=76" to setOf(PathParamIndex("p1", 1)),
						mapOf("p1" to "76", "q1" to "45")
				),
				arrayOf(
						"asd/32/tyu?q1=35&q2=&q3&q4=56" to setOf(PathParamIndex("p1", 1)),
						mapOf("p1" to "32", "q1" to "35", "q2" to "", "q4" to "56")
				),
				arrayOf("?q1=32" to emptySet<PathParamIndex>(), mapOf("q1" to "32")),
				arrayOf("" to emptySet<PathParamIndex>(), emptyMap<String, String>())
		)
	}

	private val parser = RouteArgumentsParserImpl()

	@Parameter(0)
	lateinit var input: Pair<String, Set<PathParamIndex>>

	@Parameter(1)
	lateinit var expected: Map<String, String>

	@Test
	fun `test parsing of arguments is done properly`() {
		parser.parse(input.first, input.second) shouldEqual expected
	}
}