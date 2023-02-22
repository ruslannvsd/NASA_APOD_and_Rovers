package com.example.nasaphotos.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos (
    @Json(name = "photos")        val photos: List<Photo>
        )

@JsonClass(generateAdapter = true)
data class Photo (
    @Json(name = "id")            val id: Int,
    @Json(name = "sol")           val sol: Int,
    @Json(name = "camera")        val camera: Camera,
    @Json(name = "img_src")       val src: String,
    @Json(name = "earth_date")    val earthDate: String,
    @Json(name = "rover")         val rover: Rover
        )

@JsonClass(generateAdapter = true)
data class Camera (
    @Json(name = "id")            val id: Int,
    @Json(name = "name")          val name: String,
    @Json(name = "rover_id")      val roverId: Int,
    @Json(name = "full_name")     val fullName: String
        )

@JsonClass(generateAdapter = true)
data class Rover (
    @Json(name = "id")            val id: Int,
    @Json(name = "name")          val name: String,
    @Json(name = "landing_date")  val land: String,
    @Json(name = "launch_date")   val launch: String,
    @Json(name = "status")        val status: String
        )