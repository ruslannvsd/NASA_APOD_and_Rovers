package com.example.nasaphotos.data

import com.example.nasaphotos.funs.Cons
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Apod ( // getting apod instances from Json string got from the NASA website
    @Json(name = "copyright")       var cop: String = "None given",
    @Json(name = "date")            val date: String,
    @Json(name = "explanation")     val expl: String,
    @Json(name = "hdurl")           val hdurl: String = Cons.NONE,
    @Json(name = "media_type")      val medType: String = "unknown",
    @Json(name = "service_version") val vers: String?,
    @Json(name = "title")           val title: String,
    @Json(name = "url")             val url: String = Cons.NONE
        )

data class ApodSimple( // I reconstruct the Apod class to my class including only info necessary to me
    val date: String,
    val expl: String,
    val hdurl: String,
    val medType: String,
    val title: String,
    val url: String
)