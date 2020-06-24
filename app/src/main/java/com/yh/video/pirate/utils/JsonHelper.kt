@file:JvmName("JsonHelper")

package com.yh.video.pirate.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.text.DateFormat


val gson: Gson by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    GsonBuilder()
            .setDateFormat(DateFormat.LONG)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
}


inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, TypeToken.get(T::class.java).type)
}


inline fun <reified T> JsonElement.fromJson(): T {
    return gson.fromJson(this, TypeToken.get(T::class.java).type)
}


inline fun <reified T> T.toJson(): String {
    return gson.toJson(this)
}