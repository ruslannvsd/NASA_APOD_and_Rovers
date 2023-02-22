package com.example.nasaphotos.funs

import android.content.Context
import android.content.SharedPreferences
import com.example.nasaphotos.data.ApodSimple
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GsonJson(ctx: Context) {
    val sharedPref: SharedPreferences =
        ctx.getSharedPreferences(Cons.SHARED_APOD, Context.MODE_PRIVATE)

    fun toJson(list: List<ApodSimple>) {
        val gson = Gson()
        val json = gson.toJson(list)
        val editor = sharedPref.edit()
        editor.putString(Cons.SAL, json)
        editor.apply()
    }

    fun fromJson(): List<ApodSimple> {
        val gson = Gson()
        val json = sharedPref.getString(Cons.SAL, "")
        val type: Type = object : TypeToken<List<ApodSimple>>(){}.type
        return gson.fromJson(json, type) ?: listOf()
    }
}