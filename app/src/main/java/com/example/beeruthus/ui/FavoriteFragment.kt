package com.example.beeruthus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beeruthus.databinding.ItemViewPagerBinding
import com.example.beeruthus.model.Beer
import com.example.beeruthus.model.FavoriteBeerBinder
import com.example.beeruthus.ui.adapter.BeerEvent
import com.example.beeruthus.utils.DefaultItemAnimator
import com.example.beeruthus.utils.ImageUtils
import com.example.beeruthus.viewmodel.BeerViewModel
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import mva2.adapter.util.PayloadProvider

class FavoriteFragment : Fragment() {
    lateinit var viewModel: BeerViewModel
    var binding: ItemViewPagerBinding? = null

    private val multiViewAdapter by lazy {
        MultiViewAdapter()
    }
    private val beerEvent = object : BeerEvent {
        override fun onSave(beer: Beer) {

        }

        override fun onDelete(beer: Beer) {
            viewModel.deleteBeer(beer)
            ImageUtils.deleteImage(requireContext(), beer.id?.toString().orEmpty())
        }

        override fun onUpdate(beer: Beer) {
            viewModel.updateBeer(beer)
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
        observeData()
    }

    private fun observeData() {
        viewModel.listBeerInRoom.observe(viewLifecycleOwner) {
            section.set(it)
        }
    }


    private fun setUpRecyclerBeer() {
        multiViewAdapter.registerItemBinders(FavoriteBeerBinder(beerEvent))
        multiViewAdapter.addSection(section)
        binding?.rcBeer?.itemAnimator = DefaultItemAnimator(0)
        binding?.rcBeer?.adapter = multiViewAdapter
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
}