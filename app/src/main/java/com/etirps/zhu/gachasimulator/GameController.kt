package com.etirps.zhu.gachasimulator

import android.graphics.Bitmap
import android.util.LruCache
import android.view.InputQueue
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject

class GameController(private val game_layout: View, private val queue: RequestQueue, private val imageLoader: ImageLoader): ViewController(game_layout) {

    override fun updateView() {
        //
    }

    private val redditFunctions = RedditFunctions(game_layout.context, queue)

    private val onButtonClick = View.OnClickListener { view ->
        //Toast.makeText(game_layout.context, "button clicked!!!", Toast.LENGTH_SHORT).show()
        //game_layout.findViewById<TextView>(R.id.message).text = (game_layout.context.applicationContext as ApplicationData).cashMoney.toString()
        pullCharacter()
    }


    init {
        game_layout.findViewById<Button>(R.id.button).setOnClickListener(onButtonClick)
    }

    fun pullCharacter() {
        redditFunctions.pullNewCharacter(RARITY_SCALE.values().random(), object: ServerCallback {

            override fun onSuccess(result: RedditData) {
                game_layout.findViewById<TextView>(R.id.message).text = "${result.rarity} - ${result.rarityValue}\n${result.subreddit}\n${result.title}\n${result.filename}"
                game_layout.findViewById<NetworkImageView>(R.id.image).setImageUrl(result.url, imageLoader)
                appData.memeCollection.add(result)
            }

            override fun onFailure(reason: String) {
                pullCharacter()
            }
        })
    }

}