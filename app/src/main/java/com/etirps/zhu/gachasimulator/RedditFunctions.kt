package com.etirps.zhu.gachasimulator

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.lang.StringBuilder

enum class RARITY_SCALE {
    BAD, OKAY, GOOD, GOODEST
}

data class RedditData(var title: String, var filename: String, var rarity: String)

class RedditFunctions(private val context: Context) {

    private var reddit_base_url = """https://www.reddit.com/r/"""
    private var reddit_subreddit_url = arrayOf("bossfight", "hmmm", "woof_irl", "meow_irl", "blessedimages")
    private var reddit_end_url = """/top/.json?count=20?sort=top&t=all"""
    private var rarity_list = arrayOf(arrayOf("F", "FF", "LOL", "BAD"), arrayOf("C", "CC", "HUH", "OK"), arrayOf("A", "AA", "COOL", "NICE"), arrayOf("S", "SS", "BEST", "WOW"))

    private var queue = Volley.newRequestQueue(context)

    fun pullNewCharacter(rarity: RARITY_SCALE, callback: ServerCallback) {
        getRedditPostingData(reddit_subreddit_url[(0 until reddit_subreddit_url.size).random()], rarity, callback)
    }

    private fun getRedditPostingData(subreddit: String, rarity: RARITY_SCALE, callback: ServerCallback) {
        // Create request url by combining strings
        val url = reddit_base_url + subreddit + reddit_end_url

        // make http request on the url, stringRequest is an object that will be put on the requests queue
        val stringRequest = StringRequest(Request.Method.GET, url,
            // If response returns
            Response.Listener<String> { response ->
                // Put the response into a stringbuilder so the parser reads it as raw data. Then cast the parsed data as a jsonobject and traverse the json to get to the children jsonarray
                // Then get a random post from the 20 we got (0-19) and grab the data jsonobject
                val responseJson = (Parser.default().parse(StringBuilder(response)) as JsonObject).obj("data")?.array<JsonObject>("children")?.get((0..19).random())?.obj("data")

                // Make sure we have valid data
                if(responseJson != null) {
                    Toast.makeText(context, responseJson.string("title"), Toast.LENGTH_SHORT).show()
                    val redditData = RedditData(responseJson.string("title") ?: "No Title", responseJson.string("url") ?: "", rarity_list[rarity.ordinal][(0..3).random()])
                    callback.onSuccess(redditData)
                }
            },
            // If error returns
            Response.ErrorListener { error ->
                Toast.makeText(context, "UH OH", Toast.LENGTH_SHORT).show()
                callback.onFailure(error.toString())
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}