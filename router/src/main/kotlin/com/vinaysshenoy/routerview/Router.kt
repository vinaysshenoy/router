package com.vinaysshenoy.routerview

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_ANY
import android.arch.lifecycle.Lifecycle.State.DESTROYED
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import timber.log.Timber

private val minimumLifecycleState = Lifecycle.State.INITIALIZED

class Router(
	source: LifecycleOwner,
	savedInstanceState: Bundle?,
	private val container: ViewGroup
) : LifecycleObserver {

	private var currentState = source.lifecycle.currentState

	init {
		if (source.lifecycle.currentState.isAtLeast(minimumLifecycleState)) {
			source.lifecycle.addObserver(this)
		} else {
			throw IllegalStateException(
					"Current Lifecycle state [${source.lifecycle.currentState}] < $minimumLifecycleState!"
			)
		}
	}

	@JvmOverloads constructor(
		activity: FragmentActivity,
		savedInstanceState: Bundle?, @IdRes layoutResId: Int = -1
	) :
			this(
					source = activity,
					savedInstanceState = savedInstanceState,
					container = layoutResId.let {
						var container = activity.findViewById<ViewGroup?>(layoutResId)

						if (container == null) {

							if (layoutResId == -1) {
								Timber.tag(TIMBER_TAG)
										.w("Creating container in activity! This is not recommended!")

								//Create a container and add to the activity
								container = FrameLayout(activity)
								container.layoutParams = FrameLayout.LayoutParams(
										FrameLayout.LayoutParams.MATCH_PARENT,
										FrameLayout.LayoutParams.MATCH_PARENT
								)
								activity.setContentView(container)

							} else {
								throw IllegalStateException(
										"Could not find container view with id: $layoutResId in activity $activity!"
								)
							}
						}

						container!!
					}
			)

	@OnLifecycleEvent(ON_ANY)
	fun onLifecycleEvent(
		source: LifecycleOwner,
		event: Lifecycle.Event
	) {
		this.currentState = source.lifecycle.currentState
		Timber.tag(TIMBER_TAG)
				.d("Current Lifecycle State: ${source.lifecycle.currentState}")
		if (source.lifecycle.currentState == DESTROYED) {
			source.lifecycle.removeObserver(this)
		}
	}

}