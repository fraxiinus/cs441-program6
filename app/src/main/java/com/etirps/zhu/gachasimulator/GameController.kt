package com.etirps.zhu.gachasimulator

import android.graphics.Bitmap
import android.support.constraint.ConstraintLayout
import android.util.LruCache
import android.view.InputQueue
import android.view.View
import android.widget.*
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
        view.visibility = View.GONE
        game_layout.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
        pullCharacter()
    }

    private val onCloseButtonClick = View.OnClickListener { view ->
        game_layout.findViewById<ConstraintLayout>(R.id.get_layout).visibility = View.GONE
        game_layout.findViewById<Button>(R.id.button).visibility = View.VISIBLE
    }


    init {
        game_layout.findViewById<Button>(R.id.button).setOnClickListener(onButtonClick)
        game_layout.findViewById<Button>(R.id.close_button).setOnClickListener(onCloseButtonClick)
    }

    fun pullCharacter() {
        redditFunctions.pullNewCharacter(RARITY_SCALE.values().random(), object: ServerCallback {

            override fun onSuccess(result: RedditData) {
                //game_layout.findViewById<TextView>(R.id.message).text = "${result.rarity} - ${result.rarityValue}\n${result.subreddit}\n${result.title}\n${result.filename}"
                //game_layout.findViewById<NetworkImageView>(R.id.image).setImageUrl(result.url, imageLoader)
                val getdisplay = game_layout.findViewById<ConstraintLayout>(R.id.get_layout)

                getdisplay.findViewById<NetworkImageView>(R.id.meme_iv).setImageUrl(result.url, imageLoader)
                getdisplay.findViewById<TextView>(R.id.subreddit_tv).text = result.subreddit
                getdisplay.findViewById<TextView>(R.id.title_tv).text = result.title

                when(result.rarityValue) {
                    0 -> {
                        getdisplay.findViewById<ImageView>(R.id.rarity_iv).setImageResource(R.drawable.two)
                    }
                    1 -> {
                        getdisplay.findViewById<ImageView>(R.id.rarity_iv).setImageResource(R.drawable.three)
                    }
                    2 -> {
                        getdisplay.findViewById<ImageView>(R.id.rarity_iv).setImageResource(R.drawable.four)
                    }
                    3 -> {
                        getdisplay.findViewById<ImageView>(R.id.rarity_iv).setImageResource(R.drawable.five)
                    }
                }

                getdisplay.visibility = View.VISIBLE
                game_layout.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

                appData.memeCollection.add(result)
            }

            override fun onFailure(reason: String) {
                pullCharacter()
            }
        })
    }

}