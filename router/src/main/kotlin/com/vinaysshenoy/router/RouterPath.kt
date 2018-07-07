package com.vinaysshenoy.router

private val validPathRegex = Regex("(:?[a-z0-9-_.]+)(/:?[a-z0-9-_.]+)*")

data class RouterPath(
	val path: String,
	val routeCreator: (Map<String, String>) -> Route
) {

	val routeRegex: Regex

	val pathParams: Set<PartParamIndex>

	init {

		if (!validPathRegex.matches(path)) throw IllegalArgumentException(
				"\"$path\" is not a valid path!"
		)

		pathParams = path.split("/")
				.mapIndexed { index, part -> PartParamIndex(part, index) }
				.filter { (part, _) -> part.startsWith(":") }
				.filter { (part, _) -> part.length > 1 }
				.map { it.copy(part = it.part.substring(1)) }
				.toSet()


		routeRegex = Regex(
				pathParams.fold(path, { acc, pathParam -> acc.replace(":$pathParam", "\\w+") })
		)

	}

	fun matches(path: String) = routeRegex.matches(path)

	fun parseParams(path: String): Map<String, String> {

		val map = mutableMapOf<String, String>()

		//Read path params first, then query params
		val parts = path.split('?').let {
			when {
				it.size == 2 -> it
				it.size == 1 -> listOf(it, "") //Query parameter ('?') is absent, initialize query string to empty
				it.size > 2 -> throw IllegalArgumentException("path cannot contain more than one '?' character")
				else -> throw RuntimeException("Unknown error!")
			}
		}
		return map.toMap()
	}

}

data class PartParamIndex(
	val part: String,
	val index: Int
)