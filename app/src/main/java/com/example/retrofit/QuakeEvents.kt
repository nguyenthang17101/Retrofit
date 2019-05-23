package com.example.retrofit

import com.google.gson.annotations.SerializedName

data class QuakeEventService(@SerializedName("features")var features: ArrayList<Feature>) {
    data class Feature(@SerializedName("properties")var quakeEvents: QuakeEvents)
}

data class QuakeEvents(val mag: Double,val place: String,val time: Long)

