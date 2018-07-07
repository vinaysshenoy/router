package com.vinaysshenoy.routerview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.vinaysshenoy.routerview.Router

class MainActivity: AppCompatActivity() {

	private lateinit var router: Router

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		router = Router(this, savedInstanceState, findViewById<ViewGroup>(R.id.container))
	}
}