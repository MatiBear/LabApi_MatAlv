package com.example.labapi_matalv.apiFolder

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiRequestTask(private val callback: ApiCallback) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String): String {
        val apiUrl = params[0]

        return try {
            val url = URL(apiUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            try {
                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val stringBuilder = StringBuilder()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        stringBuilder.append(line).append("\n")
                    }

                    stringBuilder.toString()
                } else {
                    "HTTP Error: $responseCode"
                }
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Exception: ${e.message}"
        }
    }

    override fun onPostExecute(result: String) {
        callback.onApiResult(result)
    }
}