package com.itssvkv.darkpix.ui.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentTabsBinding


class TabsFragment : Fragment() {
    private var binding: FragmentTabsBinding? = null
    private lateinit var tabsAdapter: TabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentTabsBinding.inflate(layoutInflater, container, false)
        initTabsAdapter()
        return binding?.root
    }

    private fun initTabsAdapter(){
        val animalsArray = arrayOf(
            "Home",
            "Search",

        )
        tabsAdapter = TabsAdapter(this)
        binding?.tabsViewPager?.adapter = tabsAdapter

        TabLayoutMediator(binding?.tabsTabLayout!!, binding?.tabsViewPager!!){ tab, position ->
            tab.text = animalsArray[position]
        }.attach()
    }


}