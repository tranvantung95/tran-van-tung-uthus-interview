package com.example.beeruthus.ui.adapter

import com.example.beeruthus.model.Beer

interface BeerEvent {
    fun onSave(beer: Beer)
    fun onDelete(beer: Beer)
    fun onUpdate(beer: Beer)
}