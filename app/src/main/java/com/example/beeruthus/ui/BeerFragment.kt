package com.example.beeruthus.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beeruthus.R
import com.example.beeruthus.databinding.ItemViewPagerBinding
import com.example.beeruthus.model.Beer
import com.example.beeruthus.model.BeerBinder
import com.example.beeruthus.ui.adapter.BeerEvent
import com.example.beeruthus.utils.DefaultItemAnimator
import com.example.beeruthus.utils.ImageUtils
import com.example.beeruthus.viewmodel.BeerViewModel
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import mva2.adapter.util.InfiniteLoadingHelper
import mva2.adapter.util.PayloadProvider

class BeerFragment : Fragment() {
    lateinit var viewModel: BeerViewModel
    var binding: ItemViewPagerBinding? = null

    private val multiViewAdapter by lazy {
        MultiViewAdapter()
    }
    private lateinit var loadMore: InfiniteLoadingHelper

    private val beerEvent = object : BeerEvent {
        override fun onSave(beer: Beer) {
            viewModel.updateBeerNote(beer)
            ImageUtils.downloadImage(
                beer.image.orEmpty(),
                requireContext(),
                beer.id?.toString().orEmpty()
            )
        }

        override fun onDelete(beer: Beer) {

        }

        override fun onUpdate(beer: Beer) {

        }

    }

    private val section = ListSection<Beer>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ItemViewPagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BeerViewModel::class.java]
        setUpRecyclerBeer()
        loadBeer(1)
        observeData()
    }

    private fun observeData() {
        viewModel.mediatorBeer.observe(viewLifecycleOwner) {
            section.set(it)
            loadMore.setPageCount(viewModel.totalPageCount - 1)// start page cua lib == 0 , start page cua lib ==1
        }
    }

    private fun setUpRecyclerBeer() {
        multiViewAdapter.registerItemBinders(BeerBinder(beerEvent))
        multiViewAdapter.addSection(section)
        binding?.rcBeer?.itemAnimator = DefaultItemAnimator(0)

        binding?.rcBeer?.adapter = multiViewAdapter
        loadMore = object : InfiniteLoadingHelper(binding?.rcBeer, R.layout.loading_item) {
            override fun onLoadNextPage(page: Int) { // start page cua lib == 0 , start page cua lib ==1
                val nextPage = page + 1
                loadBeer(nextPage)
                if (nextPage == viewModel.totalPageCount) {
                    loadMore.markAllPagesLoaded()
                } else {
                    loadMore.markCurrentPageLoaded()
                }
            }
        }
        multiViewAdapter.setInfiniteLoadingHelper(loadMore)
        section.setPayloadProvider(object : PayloadProvider<Beer> {
            override fun areContentsTheSame(oldItem: Beer?, newItem: Beer?): Boolean {
                return ( oldItem?.note == newItem?.note &&
                        oldItem?.state?.name == newItem?.state?.name)
            }

            override fun areItemsTheSame(oldItem: Beer?, newItem: Beer?): Boolean {
                return oldItem?.id != null && oldItem.id == newItem?.id

            }

            override fun getChangePayload(oldItem: Beer?, newItem: Beer?): Any {
                return Any()
            }
        })
    }

    private fun loadBeer(page: Int) {
        viewModel.getBeers(page)
    }
}