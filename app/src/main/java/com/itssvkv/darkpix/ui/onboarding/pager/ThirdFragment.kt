package com.itssvkv.darkpix.ui.onboarding.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
    private var binding: FragmentThirdBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater)
        return binding?.root
    }

}