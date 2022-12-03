package com.example.midterm

import com.google.gson.annotations.SerializedName

class MapResponse {
    @SerializedName("route")
    var route: Route? = null
}

class Route{
    @SerializedName("distance")
    var distance: Float = 0.toFloat()
    @SerializedName("time")
    var time: Int = 0
}