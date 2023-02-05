package com.example.beeruthus.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.beeruthus.R

@Entity(tableName = "beer")
data class Beer(
    @PrimaryKey
    var id: Int? = null,
    var price: String? = null,
    var name: String? = null,
    var image: String? = null,
    @Embedded
    var rating: Rating? = null,
    var note: String? = null,
    @Ignore
    var state: ItemBeerState = ItemBeerState.Save
) {
    fun getStateText(): Int? {
        return when (state) {
            ItemBeerState.Saved -> null
            ItemBeerState.Saving -> R.string.saving_text
            else -> R.string.save_text
        }
    }

    fun showSaveButton(): Boolean {
        return state != ItemBeerState.Saved
    }

    fun clone(beer: Beer): Beer {
        this.id = beer.id
        this.price = beer.price
        this.name = beer.name
        this.image = beer.image
        this.rating = beer.rating
        this.note = beer.note
        this.state = beer.state
        return this
    }
}

enum class ItemBeerState {
    Save, Saving, Saved
}
