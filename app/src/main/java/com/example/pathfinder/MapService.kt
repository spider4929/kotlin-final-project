package com.example.pathfinder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("https://www.mapquestapi.com/directions/v2/route?")
    fun getCurrentMapData(@Query("from") from: String, @Query("to") To: String, @Query("key") key: String):
            Call<MapResponse>
}