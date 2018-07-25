package com.ethemgur.qoin

import java.io.IOException
import java.io.ObjectStreamException
import java.io.Serializable

/**
 * Created by macbook on 15/07/2018.
 */
//class Quote2(val price: Double, val volume_24h: Double, val market_cap: Double, val percent_change_1h: Double, val percent_change_24h: Double, val percent_change_7d: Double) {
//
//}
//
//class Quote (val USD: Quote2) {
//
//}
//
//class Coin (val id: Int, val name: String, val symbol: String, val website_slug: String, val rank: Int, val circulating_supply: Double, val total_supply: Int, val max_supply: Double, val quotes: Quote, val last_updated: Double) {
//
//}

//class Coin {
//
//    var id: Int? = 0
//    var name: String? = ""
//    var symbol: String? = ""
//    var website_slug: String? = ""
//    var rank: Int? = 0
//    var circulating_supply: Double? = 0.0
//    var total_supply: Double? = 0.0
//    var max_supply: Double? = 0.0
//    var last_updated: Int? = 0
//    var price: Double? = 0.0
//    var volume_24h: Double? = 0.0
//    var market_cap: Double? = 0.0
//    var percent_change_1h: Double? = 0.0
//    var percent_change_24h: Double? = 0.0
//    var percent_change_7d: Double? = 0.0
//
//
//    override fun toString(): String {
//        return "name = $name"
//        return "price = $price"
//    }
//
//}

class Coin: Serializable {
    var id = 0
    var name = ""
    var symbol = ""
    var website_slug = ""
    var rank = 0
    var circulating_supply = 0.0
    var total_supply = 0.0
    var last_updated = 0
    var price = 0.0
    var volume_24h = 0.0
    var market_cap = 0.0
    var percent_change_1h = 0.0
    var percent_change_24h = 0.0
    var percent_change_7d = 0.0

    override fun toString(): String {
        return "name = $name"
    }

    @Throws(IOException::class)
    private fun writeObject(out: java.io.ObjectOutputStream) {
        out.writeInt(id)
        out.writeUTF(name)
        out.writeUTF(symbol)
        out.writeUTF(website_slug)
        out.writeInt(rank)
        out.writeDouble(circulating_supply)
        out.writeDouble(total_supply)
        out.writeInt(last_updated)
        out.writeDouble(price)
        out.writeDouble(volume_24h)
        out.writeDouble(market_cap)
        out.writeDouble(percent_change_1h)
        out.writeDouble(percent_change_24h)
        out.writeDouble(percent_change_7d)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inStream: java.io.ObjectInputStream) {
        id = inStream.readInt()
        name = inStream.readUTF()
        symbol = inStream.readUTF()
        website_slug = inStream.readUTF()
        rank = inStream.readInt()
        circulating_supply = inStream.readDouble()
        total_supply = inStream.readDouble()
        last_updated = inStream.readInt()
        price = inStream.readDouble()
        volume_24h = inStream.readDouble()
        market_cap = inStream.readDouble()
        percent_change_1h = inStream.readDouble()
        percent_change_24h = inStream.readDouble()
        percent_change_7d = inStream.readDouble()
    }

    @Throws(ObjectStreamException::class)
    private fun readObjectNoData() {}


}