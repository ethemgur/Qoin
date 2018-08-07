package com.ethemgur.qoin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

/**
 * Created by macbook on 29/07/2018.
 */
class DBHelper(val context: Context): SQLiteOpenHelper(context, DBHelper.DATABASE_NAME, null,
        DBHelper.DATABASE_VERSION) {

    private val TAG = "DBHelper"

    private val TABLE_NAME = "FavoriteCoin"
    private val COL_ID = "id"
    private val COL_COIN_ID = "coin_id"
//    private val COL_NAME = "name"
//    private val COL_SYMBOL = "symbol"
//    private val COL_WEBSITE_SLUG = "website_slug"
//    private val COL_RANK = "rank"
//    private val COL_CIRCULATING_SUPPLY = "circulating_supply"
//    private val COL_TOTAL_SUPPLY = "total supply"
//    private val COL_LAST_UPDATED = "last_updated"
//    private val COL_PRICE = "price"
//    private val COL_VOLUME_24H = "volume_24h"
//    private val COL_MARKET_CAP = "market_cap"
//    private val COL_PERCENT_CHANGE_1H = "percent_change_1h"
//    private val COL_PERCENT_CHANGE_24H = "percent_change_24h"
//    private val COL_PERCENT_CHANGE_7D = "percent_change_7d"

    companion object {
        private val DATABASE_NAME = "QOIN_SQLITE"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, $COL_COIN_ID INTEGER)"
//                ", $COL_NAME VARCHAR(256), $COL_SYMBOL " +
//                "VARCHAR(256), $COL_WEBSITE_SLUG VARCHAR(256), $COL_RANK INTEGER, " +
//                "$COL_CIRCULATING_SUPPLY FLOAT, $COL_TOTAL_SUPPLY FLOAT"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(coin: Coin) {
        Log.d(TAG, "insertData called")
        val db = this.writableDatabase
        Log.d(TAG, "insertData writableDatabase called")
        val contentValues = ContentValues()
        contentValues.put(COL_COIN_ID, coin.id)
        Log.d(TAG, "insertData contentValues.put called")
        val result = db.insert(TABLE_NAME, null, contentValues)
        Log.d(TAG, "insertData result called")
        Toast.makeText(context, if (result != -1L) "Coin is successfully added"
        else "There is an error", Toast.LENGTH_SHORT).show()
    }

    fun readData(): MutableList<Int> {
        val coinList = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val coin_id = result.getInt(result.getColumnIndex(COL_COIN_ID)).toInt()
                coinList.add(coin_id)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return coinList
    }

    fun deleteData(coin: Coin) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $COL_COIN_ID=${coin.id}")
        db.close()
    }

    fun ifExists(coin: Coin): Boolean {
        for (c in readData()) if (c == coin.id) return true
        return false
    }
}