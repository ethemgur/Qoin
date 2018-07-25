package com.ethemgur.qoin

import java.io.IOException
import java.io.ObjectStreamException
import java.io.Serializable


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