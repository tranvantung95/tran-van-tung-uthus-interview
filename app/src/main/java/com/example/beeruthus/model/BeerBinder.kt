package com.example.beeruthus.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.beeruthus.databinding.BeerItemBinding
import com.example.beeruthus.ui.adapter.BeerEvent
import mva2.extension.DBItemBinder

class BeerBinder(val beerEvent: BeerEvent) : DBItemBinder<Beer, BeerItemBinding>() {
    override fun canBindData(item: Any?): Boolean {
        return item is Beer
    }

    override fun createBinding(parent: ViewGroup?): BeerItemBinding {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        return DataBindingUtil.inflate(
            inflater,
            com.example.beeruthus.R.layout.beer_item,
            parent,
            false
        )
    }

    override fun bindModel(item: Beer?, binding: BeerItemBinding?) {
        binding?.beer = item
        binding?.listener = beerEvent

    }
}


