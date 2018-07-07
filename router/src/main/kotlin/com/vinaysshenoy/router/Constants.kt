package com.vinaysshenoy.router

internal const val TIMBER_TAG = "RouterView"
internal val validPathRegex = Regex("(:?[a-z0-9-_.]+)(/:?[a-z0-9-_.]+)*")
internal val validRouteRegex = Regex("([a-z0-9-_.]+)(/[a-z0-9-_.]+)*((\\?\\w+=\\w*)(&(\\w+=\\w*))*)?")