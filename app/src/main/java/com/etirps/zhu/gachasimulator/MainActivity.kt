package com.etirps.zhu.gachasimulator

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collection_content.*
import kotlinx.android.synthetic.main.game_content.*
import kotlinx.android.synthetic.main.shop_content.*

class MainActivity : AppCompatActivity() {

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
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_collection -> {
                if(displayedView == 0) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_right)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_left)
                    content_flipper.displayedChild = 1
                } else if(displayedView == 2) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_left)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_right)
                    content_flipper.displayedChild = 1
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shop -> {
                if(displayedView != 2) {
                    content_flipper.inAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.in_from_right)
                    content_flipper.outAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.out_from_left)
                    content_flipper.displayedChild = 2
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GameController(game_container)
        CollectionController(collection_container)
        ShopController(shop_container)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
