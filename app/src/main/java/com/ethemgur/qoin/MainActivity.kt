package com.ethemgur.qoin

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.Toast
import com.ethemgur.qoin.R.id.no_coins_text
import com.ethemgur.qoin.R.id.recycler_view
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete, ParseData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener {

    private val TAG = "MainActivity"
    private val coinRecyclerViewAdapter = CoinRecyclerViewAdapter(ArrayList())
    private var coinList = ArrayList<Coin>()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        Log.d(TAG, db.readData().size.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this, recycler_view,
                this))
        recycler_view.adapter = coinRecyclerViewAdapter

        val getRawData = GetRawData(this)
        getRawData.execute("https://api.coinmarketcap.com/v2/ticker/?sort=rank" +
                "&structure=array")

//        DECLARE DRAWER TOGGLE
//        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle (
//                this,
//                drawer_layout,
//                toolbar,
//                R.string.menu_fav,
//                R.string.search_menu_title
//        ) {
//            override fun onDrawerClosed(drawerView: View?) {
//                super.onDrawerClosed(drawerView)
//            }
//
//            override fun onDrawerOpened(drawerView: View?) {
//                super.onDrawerOpened(drawerView)
//            }
//        }
//        drawerToggle.isDrawerIndicatorEnabled = true
//        drawer_layout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//
//        navigation_view.setNavigationItemSelectedListener{
//            when (it.itemId) {
//                R.id.drawer_home -> {
//                    coinRecyclerViewAdapter.loadNewData(coinList)
//                    noListedCoins(coinList)
//                }
//
//                R.id.drawer_fav -> {
//                    coinRecyclerViewAdapter.loadNewData(getFavoriteCoins())
//                    noListedCoins(getFavoriteCoins())
//                }
//            }
//            drawer_layout.closeDrawer(GravityCompat.START)
//            true
//        }
//        END OF DRAWER TOGGLE

        tabs.addOnTabSelectedListener(object:
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        coinRecyclerViewAdapter.loadNewData(coinList)
                        noListedCoins(coinList)
                    }
                    1 -> {
                        coinRecyclerViewAdapter.loadNewData(getFavoriteCoins())
                        noListedCoins(getFavoriteCoins())
                    }
                    2 -> startActivity(Intent(this@MainActivity,
                            SearchActivity::class.java))
//                    3 -> TODO("implemet alert tab")
                }            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        coinRecyclerViewAdapter.loadNewData(coinList)
                        noListedCoins(coinList)
                    }
                    1 -> {
                        coinRecyclerViewAdapter.loadNewData(getFavoriteCoins())
                        noListedCoins(getFavoriteCoins())
                    }
                    2 -> startActivity(Intent(this@MainActivity,
                            SearchActivity::class.java))
//                    3 -> TODO("implemet alert tab")
                }            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        coinRecyclerViewAdapter.loadNewData(coinList)
                        noListedCoins(coinList)
                    }
                    1 -> {
                        coinRecyclerViewAdapter.loadNewData(getFavoriteCoins())
                        noListedCoins(getFavoriteCoins())
                    }
                    2 -> startActivity(Intent(this@MainActivity,
                            SearchActivity::class.java))
//                    3 -> TODO("implemet alert tab")
                }
            }
        }
        )
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        Log.d(TAG, "onCreateOptionsMenu called")
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

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
            Log.d(TAG, "onDownloadComplete failed with status $status. " +
                    "Error message is: $data")
        }
    }

    override fun onDataAvailable(data: ArrayList<Coin>) {
        Log.d(TAG, "onDataAvailable called")
        coinRecyclerViewAdapter.loadNewData(data)
        coinList = data
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called")
    }

    override fun onResume() {
        Log.d(TAG, "onResume called")
        super.onResume()

        var queryCoinList = ArrayList<Coin>()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(COIN_QUERY, "").toLowerCase()

        if (queryResult.isNotEmpty()) {
            for (i in coinList) {
                if (i.name.contains(queryResult, true) ||
                        i.symbol.contains(queryResult, true)) {
                    queryCoinList.add(i)
                }
            }
            Log.d(TAG, "OnResume " + queryCoinList.toString())
        } else queryCoinList = coinList

        coinRecyclerViewAdapter.loadNewData(queryCoinList)
    }

    fun getFavoriteCoins(): ArrayList<Coin> {
        Log.d(TAG, "getFavoriteCoins called")
        val favCoinList = ArrayList<Coin>()
        val favCoinIDList = db.readData()

        if (favCoinIDList.isNotEmpty()) for (i in favCoinIDList)
            for (c in coinList) if (c.id == i) favCoinList.add(c)

        return favCoinList
    }

    fun noListedCoins(list: ArrayList<Coin>) {
        if (list.isEmpty()) {
            no_coins_text.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        } else {
            no_coins_text.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
    }


}
