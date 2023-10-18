package com.itssvkv.darkpix.ui.search

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentSearchBinding
import com.itssvkv.darkpix.ui.MainActivity
import com.itssvkv.darkpix.ui.home.adapter.CategoryAdapter
import com.itssvkv.darkpix.ui.search.adapter.TrendingAdapter
import com.itssvkv.darkpix.ui.search.paging.SearchPageAdapter
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var trendingLayoutManager: StaggeredGridLayoutManager
    private lateinit var categoryLayoutManager: StaggeredGridLayoutManager
    private lateinit var searchResultLayoutManager: StaggeredGridLayoutManager
    private var searchJob: Job? = null

    @Inject
    lateinit var searchPageAdapter: SearchPageAdapter

    @Inject
    lateinit var trendingAdapter: TrendingAdapter

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTrendingRecycler()
        initCategoryRecycler()
        initTrendingItemClick()
        initCategoryItemClick()
        sendSearchQuery()
    }

    private fun initTrendingRecycler() {
        viewModel.getTrendingData()
        trendingLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        trendingLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        trendingLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        viewModel.trendingLiveData.observe(viewLifecycleOwner) {
            trendingAdapter.submitList(it)
            binding?.searchTrendingRecycler?.apply {
                layoutManager = trendingLayoutManager
                adapter = trendingAdapter
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val space = 8
                        outRect.left = space
                        outRect.right = space
                        outRect.bottom = space
                        outRect.top = space
                    }
                })
                setHasFixedSize(true)
            }
        }

    }


    private fun initCategoryRecycler() {
        viewModel.getCategoryData()
        categoryLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        categoryLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        categoryLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
            binding?.searchCategoryRecycler?.apply {
                layoutManager = categoryLayoutManager
                adapter = categoryAdapter
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val space = 8
                        outRect.left = 0
                        outRect.right = 0
                        outRect.top = space
                        outRect.bottom = space
                    }
                })
            }
        }
    }

    private fun initTrendingItemClick() {
        trendingAdapter.onTrendingItemClicked = {
            (activity as MainActivity).searchText = it.searchText
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.searchPhotosFragment)
        }
    }

    private fun initCategoryItemClick() {
        categoryAdapter.onCategoryItemClicked = {
            (activity as MainActivity).searchText = it.kind
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.searchPhotosFragment)
        }
    }


    private fun sendSearchQuery() {
        binding?.deleteIcon?.setOnClickListener {
            binding?.searchEt?.setText("")
        }
        binding?.searchEt?.watchSearch(
            action = {
                lifecycleScope.launch {
                    viewModel.searchForItem(it).collectLatest { searchPageAdapter.submitData(it) }

                }

                lifecycleScope.launch(Dispatchers.Main) {

                    binding?.recyclersLayout?.visibility = View.GONE
                    binding?.searchResultsRecycler?.visibility = View.VISIBLE

                    Log.d(SAKKA, "sendSearchQuery: ")
                    searchResultLayoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    searchResultLayoutManager.gapStrategy =
                        StaggeredGridLayoutManager.GAP_HANDLING_NONE
                    searchResultLayoutManager.gapStrategy =
                        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

                    binding?.searchResultsRecycler?.apply {
                        layoutManager = searchResultLayoutManager
                        adapter = searchPageAdapter
                        addItemDecoration(object : RecyclerView.ItemDecoration() {
                            override fun getItemOffsets(
                                outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State
                            ) {
                                super.getItemOffsets(outRect, view, parent, state)
                                val space = 10
                                outRect.bottom = space
                                outRect.left = space
                                outRect.right = space
                                if (parent.getChildLayoutPosition(view) == 0) {

                                    outRect.top = space
                                } else outRect.top = space / 2
                            }
                        })
                    }
                }
            },
            duration = 1000,
            loading = null,
            emptyAction = {
                binding?.recyclersLayout?.visibility = View.VISIBLE
                binding?.searchResultsRecycler?.visibility = View.GONE
            }
        )
    }

    private fun clickOnPhoto() {
        searchPageAdapter.onSearchPhotosClicked = {
            (activity as MainActivity).onePhotoClicked = it
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.onePhotoFragment2)
        }
    }

    private fun EditText.watchSearch(
        action: ((String) -> Unit)? = null,
        duration: Long,
        loading: (() -> Unit)? = null,
        emptyAction: (() -> Unit)? = null
    ) {
        binding?.searchEt?.doAfterTextChanged { text ->
            searchJob?.cancel()
            if (text.toString().isNotEmpty())
                binding?.deleteIcon?.visibility = View.VISIBLE
            else
                binding?.deleteIcon?.visibility = View.GONE

            loading?.invoke()

            searchJob = if (text.toString().isEmpty()) {

                lifecycleScope.launch {
                    delay(duration)
                    emptyAction?.invoke()
                }
            } else {
                lifecycleScope.launch {
                    delay(duration)
                    action?.invoke(text.toString())
                }
            }

        }
    }

}