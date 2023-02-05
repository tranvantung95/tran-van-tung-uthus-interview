package com.example.beeruthus.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.beeruthus.databinding.FavoriteBeerItemBinding
import com.example.beeruthus.ui.adapter.BeerEvent
import mva2.extension.DBItemBinder

class FavoriteBeerBinder(private val beerEvent: BeerEvent) : DBItemBinder<Beer, FavoriteBeerItemBinding>() {
    override fun canBindData(item: Any?): Boolean {
        return item is Beer
    }

    override fun createBinding(parent: ViewGroup?): FavoriteBeerItemBinding {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        return DataBindingUtil.inflate(
            inflater,
            com.example.beeruthus.R.layout.favorite_beer_item,
            parent,
            false
        )
    }

    override fun bindModel(item: Beer?, binding: FavoriteBeerItemBinding?) {
        binding?.beer = item
        binding?.listener = beerEvent
    }
}


