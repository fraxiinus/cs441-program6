package com.etirps.zhu.gachasimulator

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.LruCache
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collection_content.*
import kotlinx.android.synthetic.main.game_content.*
import kotlinx.android.synthetic.main.shop_content.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameController: GameController
    private lateinit var collectionController: CollectionController
    private lateinit var shopController: ShopController

    private lateinit var queue: RequestQueue
    private lateinit var imageLoader: ImageLoader
    private lateinit var prefs: SharedPreferences

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        // Get the index of what is currently displayed, 0 = game, 1 = collection, 3 = shop
        val displayedView = content_flipper.displayedChild

        when (item.itemId) {
            R.id.navigation_game -> {
                // Animate based on position
                if(displayedView != 0) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_left)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_right)
                    content_flipper.displayedChild = 0
                    gameController.updateView()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_collection -> {
                if(displayedView == 0) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_right)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_left)
                    content_flipper.displayedChild = 1
                    collectionController.updateView()
                } else if(displayedView == 2) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_left)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_right)
                    content_flipper.displayedChild = 1
                    collectionController.updateView()
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shop -> {
                if(displayedView != 2) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_right)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_left)
                    content_flipper.displayedChild = 2
                    shopController.updateView()
                }
                val collection = Klaxon().toJsonString((application as ApplicationData).memeCollection)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        imageLoader = ImageLoader(queue, object: ImageLoader.ImageCache {
            private var cache = LruCache<String, Bitmap>(10)

            override fun getBitmap(url: String): Bitmap? {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }

        })

        prefs = this.getSharedPreferences("com.etirps.zhu.gachasimulator", Context.MODE_PRIVATE)

        (application as ApplicationData).cashMoney = prefs.getInt("com.etirps.zhu.gachasimulator.cashmoney", 0)
        findViewById<TextView>(R.id.orb_counter).text = (application as ApplicationData).cashMoney.toString()

        gameController = GameController(game_container, queue, imageLoader)
        collectionController = CollectionController(collection_container, imageLoader)
        shopController = ShopController(shop_container)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onPause() {
        prefs.edit().putInt("com.etirps.zhu.gachasimulator.cashmoney", (application as ApplicationData).cashMoney).apply()

        val collection = Klaxon().toJsonString((application as ApplicationData).memeCollection)
        prefs.edit().putString("com.etirps.zhu.gachasimulator.collection", collection).apply()

        super.onPause()
    }

    override fun onResume() {
        (application as ApplicationData).cashMoney = prefs.getInt("com.etirps.zhu.gachasimulator.cashmoney", 0)
        val collection = prefs.getString("com.etirps.zhu.gachasimulator.collection", "[]")
        (application as ApplicationData).memeCollection = Klaxon().parseArray<RedditData>(collection)?.toMutableList() ?: mutableListOf()

        findViewById<TextView>(R.id.orb_counter).text = (application as ApplicationData).cashMoney.toString()

        super.onResume()
    }
}
