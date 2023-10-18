package com.itssvkv.darkpix.ui.home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentHomeBinding
import com.itssvkv.darkpix.ui.MainActivity
import com.itssvkv.darkpix.ui.home.adapter.CategoryAdapter
import com.itssvkv.darkpix.ui.home.paging.PhotosPageAdapter
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var adapter: CategoryAdapter

    @Inject
    lateinit var photosPageAdapter: PhotosPageAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.getCategoryData()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryRecycler()
        initPhotosRecycler()
        onSelectPhoto()
    }

    private fun initCategoryRecycler() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding?.homeRecycler?.adapter = adapter
        }
    }


    private fun initPhotosRecycler() {
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding?.homePhotosRecycler?.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = photosPageAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val space = 10
                    outRect.left = space - 2
                    outRect.right = space - 2
                    outRect.bottom = space
                    outRect.top = space


                }
            })
            setHasFixedSize(true)
            Log.d(SAKKA, "initPhotosRecycler: ")
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope.launch {
            Log.d(SAKKA, "initPhotosRecycler: w")
            viewModel.getAllPhotos().collectLatest { photosPageAdapter.submitData(it) }
        }
    }

    private fun onSelectPhoto() {
        photosPageAdapter.onItemClicked = {
            (activity as MainActivity).onePhotoClicked = it
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.onePhotoFragment2)
        }
    }


}

