package com.itssvkv.darkpix.ui.bottomnav

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentBtmNavBinding
import com.itssvkv.darkpix.models.responses.PixelsPhotosResponse
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import javax.inject.Inject

@AndroidEntryPoint
class BtmNavFragment : Fragment() {
    private var binding: FragmentBtmNavBinding? = null


    @Inject
    lateinit var navController: NavController

    @Inject
    lateinit var navHostFragment: NavHostFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBtmNavBinding.inflate(layoutInflater)
        createMenu()
        setupNavController()
        return binding?.root
    }

    private fun createMenu() {
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.home,
                R.drawable.avd_home,
                R.id.homeFragment

            ),
            CbnMenuItem(
                R.drawable.search,
                R.drawable.avd_search,
                R.id.searchFragment

            ),
            CbnMenuItem(
                R.drawable.camera,
                R.drawable.avd_camera

            ),
            CbnMenuItem(
                R.drawable.cup,
                R.drawable.avd_cup
            ),
            CbnMenuItem(
                R.drawable.profile,
                R.drawable.avd_profile
            )
        )
        binding?.navView?.setMenuItems(menuItems, 0)
    }

    private fun setupNavController() {

        navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController
        binding?.navView?.setupWithNavController(navController)
    }

}