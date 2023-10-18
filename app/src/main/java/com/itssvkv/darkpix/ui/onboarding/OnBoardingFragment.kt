package com.itssvkv.darkpix.ui.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentOnBoardingBinding
import com.itssvkv.darkpix.ui.onboarding.pager.PagerAdapter
import com.itssvkv.darkpix.utils.Common
import com.itssvkv.darkpix.utils.Common.IS_FIRST_TIME
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {
    private var binding: FragmentOnBoardingBinding? = null
    private lateinit var adapter: PagerAdapter
    private var intent: Intent? = null
    private val viewModel by viewModels<OnBoardingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        init()
        return binding?.root
    }

    private fun initAdapter() {
        adapter = PagerAdapter(this)
        binding?.let {
            it.onBoardingPager.adapter = adapter
        }
        binding?.donIndicator?.attachTo(binding!!.onBoardingPager)
    }


    private fun init() {
        initAdapter()
        initNextBtn()
        initSkipTV()
        setNextBtnVisibility()
    }

    private fun setNextBtnVisibility() {
        binding?.onBoardingPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 0 || position == 1) {
                    Log.d(SAKKA, "onPageScrolled: $position")
                    binding?.onBoardingNextBtn?.animate().apply {
                        this?.alpha(1f)
                        this?.scaleX(1f)
                        this?.duration = 500L

                    }
                } else {
                    Log.d(SAKKA, "onPageScrolled: $position")
                    binding?.onBoardingNextBtn?.animate().apply {
                        this?.alpha(0f)
                        this?.scaleX(0f)
                        this?.duration = 500L

                    }

                }
            }
        })
    }

    private fun initNextBtn() {
        binding?.onBoardingNextBtn?.setOnClickListener {
            when (binding?.onBoardingPager?.currentItem) {
                0 -> {
                    binding?.onBoardingPager?.currentItem = 1
                }

                1 -> {
                    binding?.onBoardingPager?.currentItem = 2
                }
            }
        }
    }

    private fun initSkipTV() {
        binding?.onBoardingSkipTv?.setOnClickListener {
//            intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://unsplash.com/oauth/authorize?client_id=odCQbmwqHfynMYvrTRdZ26DAoYTwf38MaikRbkEyatU&redirect_uri=myapp://callback&response_type=code&scope=public")
//            )
//            startActivity(intent!!)
            this@OnBoardingFragment.findNavController().navigate(R.id.onBoardingFragmentToAuthFragment)
            viewModel.saveToPref(requireContext(), IS_FIRST_TIME, false)
        }
    }



}