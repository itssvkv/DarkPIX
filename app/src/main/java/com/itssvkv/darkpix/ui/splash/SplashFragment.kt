package com.itssvkv.darkpix.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentSplashBinding
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding? = null
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        viewModel.init(requireContext())
        observeIsFirstLiveData()
        return binding?.root
    }

    private fun observeIsFirstLiveData() {
        viewModel.isFirstTimeLiveData.observe(viewLifecycleOwner) { isFirstTime ->
            Log.d(SAKKA, "observeIsFirstLiveData: $isFirstTime")
            when (isFirstTime) {
                true -> this@SplashFragment.findNavController().navigate(R.id.splashToOnBoarding)
                false -> {
                    viewModel.isAuthDoneLiveData.observe(viewLifecycleOwner) { isAuthDone ->
                        Log.d(SAKKA, "observeAuthDoneLiveData: $isAuthDone")
                        when (isAuthDone) {
                            true -> this@SplashFragment.findNavController()
                                .navigate(R.id.splashFragmentToBottomNavFragment)

                            false -> this@SplashFragment.findNavController()
                                .navigate(R.id.splashFragmentToAuthFragment)
                        }
                    }
                }
            }
        }

    }


}