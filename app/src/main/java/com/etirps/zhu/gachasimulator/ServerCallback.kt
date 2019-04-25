package com.etirps.zhu.gachasimulator

interface ServerCallback {
    fun onSuccess(result: RedditData)
    fun onFailure(reason: String)
}