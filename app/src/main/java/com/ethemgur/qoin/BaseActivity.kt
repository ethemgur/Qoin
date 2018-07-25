package com.ethemgur.qoin

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View


internal const val COIN_QUERY = "COIN_QUERY"
internal const val COIN_TRANSFER = "COIN_TRANSFER"

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {

    private val TAG = "BaseActivity"

    internal fun activateToolbar(enableHome: Boolean) {
        Log.d(TAG, "activateToolbar called")
        val toolbar = findViewById<View>(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }

}