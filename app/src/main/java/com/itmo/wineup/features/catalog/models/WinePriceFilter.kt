package com.itmo.wineup.features.catalog.models

data class WinePriceFilter(
    var minPrice: Int,
    var maxPrice: Int,
    var itemWithDiscount: Boolean
)