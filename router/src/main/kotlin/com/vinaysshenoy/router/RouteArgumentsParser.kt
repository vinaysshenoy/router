package com.vinaysshenoy.router

interface RouteArgumentsParser {

	fun parse(
		routeQuery: String,
		pathParams: Set<PathParamIndex>
	): Map<String, String>
}

class RouteArgumentsParserImpl : RouteArgumentsParser {

	override fun parse(
		routeQuery: String,
		pathParams: Set<PathParamIndex>
	): Map<String, String> {
		val map = mutableMapOf<String, String>()

		//Read path params first, then query params
		val parts = routeQuery.split('?')
				.let {
					when {
						it.size == 2 -> it
						it.size == 1 -> it + "" //Query parameter ('?') is absent, initialize query string to empty
						it.size > 2 -> throw IllegalArgumentException(
								"routeQuery cannot contain more than one '?' character"
						)
						else -> throw RuntimeException("Unknown error!")
					}
				}

		map.putAll(readPathParams(parts[0], pathParams))
		map.putAll(readQueryParams(parts[1]))

		return map.toMap()
	}

	private fun readQueryParams(queryPart: String) =
		queryPart
				.split("&")
				.map { it.split("=") }
				.filter { it.size == 2 }
				.map { it[0] to it[1] }
				.toMap()

	private fun readPathParams(
		pathPart: String,
		pathParams: Set<PathParamIndex>
	): Map<String, String> {

		val requiredPartIndicesInPath = pathParams.map { it.index }

		val pathParts = pathPart.split("/")
				.mapIndexed { index, part -> index to part }
				.filter { (index, _) -> index in requiredPartIndicesInPath }
				.associateBy({ it.first }, { it.second })

		if (pathParts.size != pathParams.size) throw IllegalArgumentException(
				"Expected number of path params: ${pathParams.size}, Found: ${pathParts.size}! Something went wrong with routing!"
		)

		return pathParams.associateByTo(
				destination = mutableMapOf(),
				keySelector = { pathParamIndex: PathParamIndex -> pathParamIndex.name },
				valueTransform = { pathParamIndex -> pathParts[pathParamIndex.index]!! }
		)

	}
}