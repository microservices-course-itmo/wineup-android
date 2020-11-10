package com.itmo.wineup.features.catalog.models

import java.io.Serializable

data class FeedbackModel(
    val name: String,
    val comment: String,
    val rate: Float,
    val date: String
):Serializable
