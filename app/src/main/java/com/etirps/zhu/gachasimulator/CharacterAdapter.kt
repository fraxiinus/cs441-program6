package com.etirps.zhu.gachasimulator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

data class ViewHolder(var title: TextView?, var subreddit: TextView?, var rarity: ImageView?, var image: NetworkImageView?)

class CharacterAdapter(context: Context, characters: List<RedditData>, var imageLoader: ImageLoader): ArrayAdapter<RedditData>(context, R.layout.character_item, characters.reversed()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var cview = convertView
        val character = getItem(position)

        lateinit var viewHolder: ViewHolder

        if(cview == null) {
            val inflater = LayoutInflater.from(context)
            cview = inflater.inflate(R.layout.character_item, parent, false)
            viewHolder = ViewHolder(title = cview?.findViewById(R.id.title_tv),
                                    subreddit = cview?.findViewById(R.id.subreddit_tv),
                                    rarity = cview?.findViewById(R.id.rarity_iv),
                                    image = cview?.findViewById(R.id.iv1))
            cview?.tag = viewHolder
        } else {
            viewHolder = cview.tag as ViewHolder
        }

        viewHolder.title?.text = character?.title
        //viewHolder.rarity?.text = character?.rarity
        viewHolder.image?.setImageUrl(character?.url, imageLoader)
        viewHolder.subreddit?.text = character?.subreddit

        when(character?.rarityValue) {
            0 -> {
                //viewHolder.rarity?.setTextAppearance(R.style.ZeroRarityText)
                viewHolder.rarity?.setImageResource(R.drawable.two)
            }

            1 -> {
                //viewHolder.rarity?.setTextAppearance(R.style.OneRarityText)
                viewHolder.rarity?.setImageResource(R.drawable.three)
            }

            2 -> {
                //viewHolder.rarity?.setTextAppearance(R.style.TwoRarityText)
                viewHolder.rarity?.setImageResource(R.drawable.four)
            }

            3 -> {
                //viewHolder.rarity?.setTextAppearance(R.style.ThreeRarityText)
                viewHolder.rarity?.setImageResource(R.drawable.five)
            }
        }

        return cview
    }
}