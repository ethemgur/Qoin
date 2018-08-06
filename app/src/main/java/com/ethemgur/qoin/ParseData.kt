package com.ethemgur.qoin

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject


class ParseData(private val listener: OnDataAvailable): AsyncTask<String, Void, ArrayList<Coin>>() {

    private val TAG = "ParseData"

    interface OnDataAvailable {
        fun onDataAvailable(data: ArrayList<Coin>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Coin> {
        Log.d(TAG, "doInBackground called")
        val coinList = ArrayList<Coin>()

        val jsonData = JSONObject(params[0])
        val dataArray = jsonData.getJSONArray("data")

        Log.d(TAG, "doInBackground ended")

        for (i in 0 until dataArray.length()) {

            try {
                val jsonCoin = dataArray.getJSONObject(i)
                val jsonCoinUSD = jsonCoin.getJSONObject("quotes").getJSONObject("USD")
                val coin = Coin()

                coin.id = jsonCoin.getInt("id")
                coin.name = jsonCoin.getString("name")
                coin.symbol = jsonCoin.getString("symbol")
                coin.website_slug = jsonCoin.getString("website_slug")
                coin.rank = jsonCoin.getInt("rank")
                coin.circulating_supply = jsonCoin.getDouble("circulating_supply")
                coin.total_supply = jsonCoin.getDouble("total_supply")
                coin.last_updated = jsonCoin.getInt("last_updated")
                coin.price = jsonCoinUSD.getDouble("price").toString().subSequence(0,6).toString().toDouble()
                coin.volume_24h = jsonCoinUSD.getDouble("volume_24h")
                coin.market_cap = jsonCoinUSD.getDouble("market_cap")
                coin.percent_change_1h = jsonCoinUSD.getDouble("percent_change_1h")
                coin.percent_change_24h = jsonCoinUSD.getDouble("percent_change_24h")
                coin.percent_change_7d = jsonCoinUSD.getDouble("percent_change_7d")

                coinList.add(coin)
                Log.d(TAG, coin.toString())
                Log.d(TAG, "doInBackground ended")

            } catch (e: Exception) {
                Log.d(TAG, "doInBackground ended with error: ${e.message}")
                listener.onError(e)
                continue
            }
        }

        return coinList
    }

    override fun onPostExecute(result: ArrayList<Coin>) {
        Log.d(TAG, "onPostExecute called")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }

}