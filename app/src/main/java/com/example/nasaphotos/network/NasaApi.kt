package com.example.nasaphotos.network

import com.example.nasaphotos.data.Apod
import com.example.nasaphotos.data.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {

    @GET("/mars-photos/api/v1/rovers/{rover}/photos")
    fun photos(
        @Path("rover") rover: String,
        @Query("sol") sol: String,
        @Query("api_key") api: String
    ) : Call<Photos>

    @GET("/planetary/apod")
    fun apd(
        @Query("count") count: String,
        @Query("api_key") api: String
    ) : Call<List<Apod>>

    @GET("/planetary/apod")
    fun today(
        @Query("api_key") api: String
    ) : Call<Apod>

}

// https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY