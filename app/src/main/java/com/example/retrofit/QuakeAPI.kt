package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface QuakeAPI {
    @GET("earthquakes/feed/v1.0/summary/4.5_week.geojson")
    fun getQuakes(): Call<QuakeEventService>
}

