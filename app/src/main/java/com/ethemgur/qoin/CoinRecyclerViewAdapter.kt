package com.ethemgur.qoin

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class CoinViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var symbol: TextView = view.findViewById(R.id.symbol)
    var name: TextView = view.findViewById(R.id.name)
    var change: TextView = view.findViewById(R.id.change)
    var price: TextView = view.findViewById(R.id.price)
    var upArrow: ImageView = view.findViewById(R.id.upArrow)
    var downArrow: ImageView = view.findViewById(R.id.downArrow)
}


class CoinRecyclerViewAdapter(private var coinList: List<Coin>): RecyclerView.Adapter<CoinViewHolder>() {
    private val TAG = "CoinRecyclerAdapter"
    private val GREEN = Color.parseColor("#00CC00")
    private val RED = Color.parseColor("#FF0000")

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
        if (coinList.isNotEmpty()){
            val coinItem = coinList[position]
            holder.symbol.text = coinItem.symbol
            holder.name.text = coinItem.name
            holder.change.text = coinItem.percent_change_24h.toString() + "%"
            holder.price.text = "$" + coinItem.price.toString()

            if (coinItem.percent_change_24h >= 0) {
                holder.change.setTextColor(GREEN)
                holder.downArrow.visibility = View.INVISIBLE
                holder.upArrow.visibility = View.VISIBLE

            } else {
                holder.change.setTextColor(RED)
                holder.upArrow.visibility = View.INVISIBLE
                holder.downArrow.visibility = View.VISIBLE
            }
        }
    }
}