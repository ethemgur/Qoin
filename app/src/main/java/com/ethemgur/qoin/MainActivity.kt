package com.ethemgur.qoin

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ethemgur.qoin.R.id.recycler_view
import com.ethemgur.qoin.R.id.toolbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete, ParseData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener {

    private val TAG = "MainActivity"
    private val coinRecyclerViewAdapter = CoinRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activateToolbar(false)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view, this))
        recycler_view.adapter = coinRecyclerViewAdapter

        val getRawData = GetRawData(this)
        getRawData.execute("https://api.coinmarketcap.com/v2/ticker/?sort=rank" +
                "&structure=array")


    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick called")
        val coin = coinRecyclerViewAdapter.getCoin(position)
        if (coin != null) {
            val intent = Intent(this, CoinDetailsActivity::class.java)
            intent.putExtra(COIN_TRANSFER, coin)
            startActivity(intent)
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick called")
        val coin = coinRecyclerViewAdapter.getCoin(position)
        if (coin != null) {
            val intent = Intent(this, CoinDetailsActivity::class.java)
            intent.putExtra(COIN_TRANSFER, coin)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected called")
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete called, data is $data")

            val parseData = ParseData(this)
            parseData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with status $status. Error message is: $data")
        }
    }

    override fun onDataAvailable(data: ArrayList<Coin>) {
        Log.d(TAG, "onDataAvailable called")
        coinRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called")
    }

    override fun onResume() {
        Log.d(TAG, "onResume called")
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(COIN_QUERY, "")

        if (queryResult.isNotEmpty()) {
            val getRawData = GetRawData(this)
            // Pass Query Result
            getRawData.execute("https://api.coinmarketcap.com/v2/ticker/?sort=rank" +
                    "&structure=array")
        }
    }
}
