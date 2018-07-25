 package com.ethemgur.qoin

import android.os.Bundle
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
