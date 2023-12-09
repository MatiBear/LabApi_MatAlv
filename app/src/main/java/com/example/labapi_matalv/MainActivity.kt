package com.example.labapi_matalv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.labapi_matalv.apiFolder.ApiCallback
import com.example.labapi_matalv.apiFolder.ApiRequestTask
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ApiCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initial API request
        requestRandomJoke()

        // Set up the button click listener
        val refreshButton: Button = findViewById(R.id.button)
        refreshButton.setOnClickListener {
            // Trigger API request when the button is clicked
            requestRandomJoke()
        }
    }

    private fun requestRandomJoke() {
        val apiUrl = getString(R.string.randomJokeUrl)
        val apiRequestTask = ApiRequestTask(this)
        apiRequestTask.execute(apiUrl)
    }

    override fun onApiResult(result: String) {
        // Handle the API response in the UI thread
        if (result.isNotBlank()) {
            Log.d(getString(R.string.apirequesttask), getString(R.string.raw_api_response, result))  // Add this line for logging
            try {
                // Assuming the response is in JSON format, parse it and extract the joke
                val jsonResponse = JSONObject(result)
                val joke = jsonResponse.getString("joke")

                // Update the UI with the new joke
                val jokeTextView: TextView = findViewById(R.id.jokeTextView)
                jokeTextView.text = joke
            } catch (e: JSONException) {
                e.printStackTrace()
                // Handle the case where JSON parsing failed
            }
        } else {
            // Handle the case where the API request failed
        }
    }
}