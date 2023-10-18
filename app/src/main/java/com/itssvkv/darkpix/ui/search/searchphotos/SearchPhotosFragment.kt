package com.itssvkv.darkpix.ui.search.searchphotos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentSearchPhotosBinding
import com.itssvkv.darkpix.ui.MainActivity
import com.itssvkv.darkpix.ui.search.SearchViewModel
import com.itssvkv.darkpix.ui.search.paging.SearchPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchPhotosFragment : Fragment() {
    private var binding: FragmentSearchPhotosBinding? = null
    private val viewModel by viewModels<SearchViewModel>()
    private var searchText: String = ""
    private lateinit var gridLayoutManager: StaggeredGridLayoutManager

    @Inject
    lateinit var searchPageAdapter: SearchPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchText = (activity as MainActivity).searchText

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPhotosBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectSearchPhotos()
        initSearchPhotoRecycler()
        onSearchPhotoClicked()
        initBackClick()
    }

    private fun collectSearchPhotos() {
        lifecycleScope.launch {
            viewModel.searchForItem(searchText)
                .collectLatest { searchPageAdapter.submitData(it) }
        }
    }

    private fun initSearchPhotoRecycler() {

        binding?.searchPhotosContentTv?.text = searchText

        gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        gridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        gridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding?.searchPhotosRecycler?.apply {
            layoutManager = gridLayoutManager
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
                    outRect.left = space
                    outRect.right = space
                    outRect.bottom = space
                    if (parent.getChildLayoutPosition(view) == 0) {
                        outRect.top = space
                    } else outRect.top = 0
                }
            })
            setHasFixedSize(true)

        }

    }

    private fun onSearchPhotoClicked() {
        searchPageAdapter.onSearchPhotosClicked = {
            (activity as MainActivity).onePhotoClicked = it
            this@SearchPhotosFragment.findNavController()
                .navigate(R.id.searchPhotosToOnePhoto)
        }
    }


    private fun initBackClick() {
        binding?.searchPhotosBackTv?.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}