package com.example.beeruthus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beeruthus.R
import com.example.beeruthus.databinding.ActivityMainBinding
import com.example.beeruthus.ui.adapter.BeerPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val tabName = arrayOf("Beer", "Favorite")
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val viewPager = binding.pager
        val tabLayout = binding.tabLayout
        val adapter = BeerPageAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabName[position]
        }.attach()
    }

}