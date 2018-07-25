package com.ethemgur.qoin

import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ethemgur.qoin.R.id.price

/**
 * Created by macbook on 17/07/2018.
 */
class CoinViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var name: TextView = view.findViewById(R.id.name)
    var price: TextView = view.findViewById(R.id.price)
}


class CoinRecyclerViewAdapter(private var coinList: List<Coin>): RecyclerView.Adapter<CoinViewHolder>() {
    private val TAG = "CoinRecyclerAdapter"

    fun loadNewData(newCoins: List<Coin>) {
        coinList = newCoins
        notifyDataSetChanged()
    }

    fun getCoin(position: Int): Coin? {
        return if (coinList.isNotEmpty()) coinList[position] else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        Log.d(TAG, "onCreateviewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount called")
        return if (coinList.isNotEmpty()) coinList.size else 1
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        if (coinList.isEmpty()) {
            holder.name.setText(R.string.empty_coin)
            holder.price.setText("")
        } else {
            val coinItem = coinList[position]
            holder.name.text = coinItem.name
            holder.price.text = coinItem.price.toString()
        }
    }
}