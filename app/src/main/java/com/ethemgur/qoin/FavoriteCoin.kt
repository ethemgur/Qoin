package com.ethemgur.qoin

import android.support.v7.app.AppCompatActivity
import android.util.Log


class FavoriteCoin(private val listener: onFavoriteCoinsAvailable): BaseActivity() {

    private val TAG = "FavoriteCoin"

    interface onFavoriteCoinsAvailable {
        fun onFavoriteCoinsAvailable()
    }
    fun getFavoriteCoins() {
        val favoriteText = application.assets.open("favorites.txt").bufferedReader().use{it.readText()}
        Log.d(TAG, favoriteText)

    }

    fun writeFavoriteCoins() {}
}