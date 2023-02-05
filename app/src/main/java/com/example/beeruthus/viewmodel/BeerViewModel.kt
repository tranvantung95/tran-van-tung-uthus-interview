package com.example.beeruthus.viewmodel

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.beeruthus.UthusApp
import com.example.beeruthus.model.Beer
import com.example.beeruthus.model.ItemBeerState
import com.example.beeruthus.repo.BeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.FieldPosition

class BeerViewModel : BaseViewModel() {

    private val beerRepository by lazy {
        BeerRepository()
    }
    private val beerDao = UthusApp.db.beerDao()
    val listBeerInRoom = Transformations.switchMap(beerDao.getAll()) {
        val beer = MutableLiveData<List<Beer>>()
        beer.value = it.map {
            it.apply {
                state = ItemBeerState.Saved
            }
        }
        beer
    }
    private val limit = 10
    private var canLoadMore = true
    private val listBeerInSever = MutableLiveData<MutableList<Beer>>()

    val mediatorBeer = MediatorLiveData<List<Beer>>().apply {
        addSource(listBeerInSever) { beerInSever ->
            val beersInRoom = listBeerInRoom.value ?: mutableListOf()
            val result = transformData(beerInSever, beersInRoom)
            this.postValue(result)
        }

        addSource(listBeerInRoom) { beersInRoom ->
            val beerInSever = listBeerInSever.value ?: mutableListOf()
            val result = transformData(beerInSever, beersInRoom)
            this.postValue(result)
        }

    }

    private fun transformData(beerInSever: List<Beer>, beersInRoom: List<Beer>): List<Beer> {
        val mapResult = hashMapOf<Int, Beer?>()
        val result = mutableListOf<Beer>()
        beersInRoom.forEach {
            if (it.id != null) {
                mapResult[it.id!!] = it
            }
        }
        result.addAll(beerInSever.map { item ->
            if (item.id != null && mapResult[item.id!!] != null) {
                mapResult[item.id]!!.copy()
            } else {
                item
            }
        })
        return result
    }

    var totalPageCount = 0
    fun getBeers(page: Int) {
        viewModelScope.launch {
            if (canLoadMore) {
                safeModelCall {
                    beerRepository.getBeer(page, limit)
                }?.let {
                    totalPageCount = it.total?.div(limit) ?: 1
                    canLoadMore = it.loadMore ?: true
                    val result = (listBeerInSever.value ?: mutableListOf()).apply {
                        this.addAll(it.data ?: mutableListOf())
                    }
                    listBeerInSever.postValue(result)
                }
            }
        }
    }

    fun updateBeerNote(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            beerDao.insertAll(beer)
        }
    }

    fun updateBeer(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            beerDao.updateBeer(beer)
            mediatorBeer.postValue(mediatorBeer.value?.toMutableList()?.apply {
                val pos = this.indexOfFirst {
                    it.id == beer.id
                }

                if (pos != -1) {
                    this[pos] = beer
                }
            })
        }
    }

    fun deleteBeer(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            beerDao.deleteBeer(beer)
        }
    }

}