package com.etirps.zhu.gachasimulator

import android.content.Context
import android.content.res.Resources

/**
 * Return resource id number for given name
 */
fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}
