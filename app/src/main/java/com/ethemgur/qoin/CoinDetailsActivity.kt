 package com.ethemgur.qoin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_coin_details.*
import kotlinx.android.synthetic.main.content_coin_details.*


class CoinDetailsActivity : BaseActivity() {

    private val TAG = "CoinDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        activateToolbar(true)

        val coin = intent.getSerializableExtra(COIN_TRANSFER) as Coin

        coin_name.text = coin.name
        coin_price.text = coin.price.toString()

        add_fav.setOnClickListener {
            Log.d(TAG, "fav button clicked")
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
            db.insertData(coin.id)
        }
    }

}
