package com.itssvkv.darkpix.ui.onboarding.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentFirstBinding
import com.itssvkv.darkpix.ui.onboarding.OnBoardingFragment


class FirstFragment : Fragment() {
    private var binding: FragmentFirstBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }



}