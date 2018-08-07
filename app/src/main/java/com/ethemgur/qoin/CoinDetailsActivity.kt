 package com.ethemgur.qoin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_coin_details.*
import kotlinx.android.synthetic.main.content_coin_details.*


class CoinDetailsActivity : BaseActivity() {

    private val TAG = "CoinDetailsActivity"
    private val GREEN = Color.parseColor("#00CC00")
    private val RED = Color.parseColor("#FF0000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        activateToolbar(true)


        val coin = intent.getSerializableExtra(COIN_TRANSFER) as Coin

        if(db.ifExists(coin)) add_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
        else add_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)

        supportActionBar?.setTitle(coin.symbol)

//        SET VIEW VALUES
        coin_change1h.text = coin.percent_change_1h.toString() + "%"
        coin_change24h.text = coin.percent_change_24h.toString() + "%"
        coin_change7d.text = coin.percent_change_7d.toString() + "%"

//        SET TEXT COLORS
        coin_change1h.setTextColor(if (coin.percent_change_1h >= 0) GREEN else RED)
        coin_change24h.setTextColor(if (coin.percent_change_24h >= 0) GREEN else RED)
        coin_change7d.setTextColor(if (coin.percent_change_7d >= 0) GREEN else RED)

        add_fav.setOnClickListener {
            Log.d(TAG, "fav button clicked")
            if (db.ifExists(coin)) {
                db.deleteData(coin)
                add_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            } else {
                db.insertData(coin)
                add_fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            }
        }
    }

}
