package com.example.beeruthus.utils

import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.beeruthus.R
import com.example.beeruthus.model.Beer
import com.example.beeruthus.model.ItemBeerState
import java.io.File


@BindingAdapter("beer")
fun loadImage(view: AppCompatImageView, beer: Beer) {
    val context = view.context
    when (beer.state) {
        ItemBeerState.Saved -> {
            val fileName = beer.id.toString()
            Glide.with(view.context)
                .load(File("${context.filesDir}/$fileName"))
                .placeholder(CircularProgressDrawable(view.context).apply {
                    this.strokeWidth = 5f
                    this.centerRadius = 30f
                    this.start()
                })
                .error(R.mipmap.ic_launcher)
                .into(view);
        }
        else -> {
            Glide.with(view.context).load(beer.image)
                .placeholder(CircularProgressDrawable(view.context).apply {
                    this.strokeWidth = 5f
                    this.centerRadius = 30f
                    this.start()
                })
                .error(R.mipmap.ic_launcher)
                .into(view)
        }
    }

}

@BindingAdapter("stringResource")
fun getTextFromResource(textView: AppCompatButton, res: Int?) {
    try {
        if (res == null) return
        textView.text = textView.context.getString(res)
    } catch (ex: java.lang.Exception) {
        return
    }
}

