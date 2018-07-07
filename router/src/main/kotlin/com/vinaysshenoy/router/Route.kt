package com.vinaysshenoy.router

import android.annotation.SuppressLint
import android.content.Context
import android.widget.FrameLayout
import java.util.UUID

@SuppressLint("ViewConstructor")
abstract class Route(context: Context) : FrameLayout(context) {

	val uuid = UUID.randomUUID()
			.toString()

	open fun onLoad(args: Map<String, String>) {}

	open fun onUnload() {}
}