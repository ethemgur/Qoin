package com.ethemgur.qoin

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData(private val listener: OnDownloadComplete): AsyncTask<String, Void, String>() {

    private val TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called, parameter is $result")
        listener.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: MalformedURLException) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            Log.d(TAG, "doInBackground: Invalid URL ${e.message}")
            return "doInBackground: Invalid URL ${e.message}"
        } catch (e: IOException) {
            downloadStatus = DownloadStatus.FAILED_OR_EMPTY
            Log.d(TAG, "doInBackground: IOException reading data ${e.message}")
            return "doInBackground: IOException reading data ${e.message}"
        } catch (e: SecurityException) {
            downloadStatus = DownloadStatus.PERMISSIONS_ERROR
            Log.d(TAG, "doInBackground: Security exception ${e.message}")
            return "doInBackground: Security exception ${e.message}"
        } catch (e: Error) {
            downloadStatus = DownloadStatus.ERROR
            Log.d(TAG, "doInBackground: Unknown error ${e.message}")
            return "doInBackground: Unknown error ${e.message}"
        }

    }

}