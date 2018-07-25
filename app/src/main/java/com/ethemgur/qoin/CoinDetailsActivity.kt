 package com.ethemgur.qoin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_coin_details.*
import kotlinx.android.synthetic.main.content_coin_details.*

 class CoinDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        activateToolbar(true)

        val coin = intent.getSerializableExtra(COIN_TRANSFER) as Coin

        coin_name.text = coin.name
        coin_price.text = coin.price.toString()
    }

}
