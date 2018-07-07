package com.vinaysshenoy.router

private val validPathRegex = Regex("(:?[a-z0-9-_.]+)(/:?[a-z0-9-_.]+)*")

data class RouterPath(
	val path: String,
	val routeCreator: (Map<String, String>) -> Route
) {

	val routeRegex: Regex

	val pathParams: Set<PathParamIndex>

	init {

		if (!validPathRegex.matches(path)) throw IllegalArgumentException(
				"\"$path\" is not a valid path!"
		)

		pathParams = path.split("/")
				.mapIndexed { index, part -> PathParamIndex(part, index) }
				.filter { (name, _) -> name.startsWith(":") }
				.filter { (name, _) -> name.length > 1 }
				.map { it.copy(name = it.name.substring(1)) }
				.toSet()


		routeRegex = Regex(
				pathParams.fold(path, { acc, pathParam -> acc.replace(":$pathParam", "\\w+") })
		)

	}

	fun matches(path: String) = routeRegex.matches(path)
}

