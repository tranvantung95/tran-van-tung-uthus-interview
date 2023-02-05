package com.example.beeruthus.model

 class BeerResponse {
    var status: String? = null
    var total: Int? = null
    var message: String? = null
    var data: MutableList<Beer>? = null
    var loadMore: Boolean? = false
}