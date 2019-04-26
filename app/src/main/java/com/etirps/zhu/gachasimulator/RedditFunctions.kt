package com.etirps.zhu.gachasimulator

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.lang.StringBuilder

enum class RARITY_SCALE {
    BAD, OKAY, GOOD, GOODEST
}

data class RedditData(var title: String, var subreddit: String, var filename: String, var url: String, var image: Bitmap? = null, var isSFW: Boolean, var rarity: String, var rarityValue: Int)

class RedditFunctions(private val context: Context, private var queue: RequestQueue) {

    private var reddit_base_url = """https://www.reddit.com/r/"""
    private var reddit_subreddit_url = arrayOf("bossfight", "hmmm", "woof_irl", "meow_irl", "blessedimages")
    private var reddit_end_url = """/top/.json?count=20?sort=top&t=all"""
    private var rarity_list = arrayOf(arrayOf("F", "FF", "GARBAGE", "TRASH"), arrayOf("C", "CC", "MEH", "BLEH"), arrayOf("A", "AA", "COOL", "NEAT"), arrayOf("S", "SS", "BEST", "SSSRU+"))

    fun pullNewCharacter(rarity: RARITY_SCALE, callback: ServerCallback) {
        // Download reddit data
        getRedditPostingData(reddit_subreddit_url[(0 until reddit_subreddit_url.size).random()], rarity, object: ServerCallback {
            // Reddit data returned
            override fun onSuccess(result: RedditData) {
                // Make sure post is sfw and file is acceptable type
                if(!result.isSFW || result.filename.isBlank()) {
                    callback.onFailure("Invalid post, try again")
                } else {
                    callback.onSuccess(result)
                }
            }

            override fun onFailure(reason: String) {
                callback.onFailure(reason)
            }
        })
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
                    //Toast.makeText(context, responseJson.string("title"), Toast.LENGTH_SHORT).show()

                    val redditData = RedditData(responseJson.string("title") ?: "No Title",
                                                subreddit,
                                                isValidUrl(responseJson.string("url") ?: ""),
                                                responseJson.string("url") ?: "",
                                                null,
                                                !(responseJson.boolean("over_18") ?: false),
                                                rarity_list[rarity.ordinal][(0..3).random()],
                                                rarity.ordinal)

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

    private fun isValidUrl(url: String): String {
        // Find the last slash that starts the filename
        val lastForwardSlash = url.lastIndexOf('/', url.length, true)

        // Check if the filetype is acceptable
        val acceptableFileType = url.endsWith("jpg", true) || url.endsWith("png", true) || url.endsWith("jpeg", true)

        // Return blank if url is no good
        if(lastForwardSlash <= 0 || !acceptableFileType) {
            return ""
        }

        // Return filename
        return url.substring(lastForwardSlash + 1, url.length)
    }
}