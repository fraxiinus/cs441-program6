# Meme Emblem, Heroes!

This is a [Gacha game](https://en.wikipedia.org/wiki/Gacha_game) where you get jpegs of memes instead of jpegs of fictional characters. 

It's actually scary how addicting this formula is. We made this as a joke but we found ourselves constantly spamming for new rare memes.

## [Video Demo](https://www.youtube.com/watch?v=f2IjES225_A&feature=youtu.be)

## Cool features
* Connects to Reddit API to ~~STEAL~~ borrow a random top meme from the past week. Uses [Volley](https://developer.android.com/training/volley) to make HTTP requests.
* There is a filter in place to make sure no posts that are NSFW or are incorrect file types are used.
* Memes are assigned a random rarity, from 2 star garbage to 5 star bestest ever.
* Collection screen uses a ListView attached to a custom ArrayAdapter which allows the listed items to have custom layouts.
* Your collection and wallet persists between sessions by using Android's built in [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) system.
* Bottom navigation bar actions are tied to a ViewFlipper that flips between all the different layouts for each screen. A custom controller was written to update views when changing screens.