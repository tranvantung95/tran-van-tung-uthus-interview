package com.example.beeruthus.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beeruthus.model.Beer

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer")
    fun getAll(): LiveData<List<Beer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg beers: Beer)

    @Query("SELECT EXISTS(SELECT * FROM beer WHERE id = :id)")
    fun isRowIsExist(id: Int): LiveData<Boolean>

    @Update
   suspend fun updateBeer(beer: Beer)

    @Delete
    suspend fun deleteBeer(beer: Beer)
}