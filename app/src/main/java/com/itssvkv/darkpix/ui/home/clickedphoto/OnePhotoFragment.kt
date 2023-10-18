package com.itssvkv.darkpix.ui.home.clickedphoto

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentOnePhotoBinding
import com.itssvkv.darkpix.models.responses.PixelsPhotosResponse
import com.itssvkv.darkpix.ui.MainActivity
import com.itssvkv.darkpix.utils.Common.SAKKA
import jp.wasabeef.glide.transformations.BlurTransformation


class OnePhotoFragment : Fragment() {

    private var binding: FragmentOnePhotoBinding? = null
    private var onePhotoClicked: PixelsPhotosResponse.Photo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).onePhotoClicked.let {
            onePhotoClicked = it
        }

        Log.d(SAKKA, "onCreate: $onePhotoClicked ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnePhotoBinding.inflate(layoutInflater, container, false)
        Log.d(SAKKA, "onCreateView: $onePhotoClicked")
        init()
        return binding?.root
    }

    override fun onPause() {
        super.onPause()
        onePhotoClicked = null
        Log.d(SAKKA, "onPause")
    }

    private fun init() {
//        if (onePhotoClicked != null) {
            showPhoto()
            setBottomSheet()
            initClicks()
//        } else if (onSearchPhotoClicked != null) {
//            showPhotoFromSearch()
//            setBottomSheetFromSearch()
//            initClicks()
//        } else {
//            Log.d(SAKKA, "init: ")
//        }
    }

    private fun showPhoto() {
        Glide.with(binding?.onePhotoBackGroundIv?.context!!)
            .load(onePhotoClicked?.src?.portrait)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25)))
            .into(binding?.onePhotoBackGroundIv!!)

        Glide.with(binding?.onePhotoMainIv?.context!!)
            .load(onePhotoClicked?.src?.portrait)
            .into(
                binding?.onePhotoMainIv!!
            )
    }

    private fun setBottomSheet() {
        BottomSheetBehavior.from(binding?.onePhotoBottomSheet!!).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding?.bottomSheetPhotoPhotographerTv?.text = onePhotoClicked!!.photographer[0].toString()
        binding?.onePhotoCardView?.setCardBackgroundColor(Color.parseColor(onePhotoClicked!!.avg_color))
        binding?.bottomSheetPhotographerTv?.text = onePhotoClicked!!.photographer
        binding?.bottomSheetDesTv?.text = onePhotoClicked!!.alt

    }



    private fun initClicks() {
        binding?.onePhotoBackTv?.setOnClickListener {
            activity?.onBackPressed()
//            this@OnePhotoFragment.findNavController()
//                .navigate(R.id.onePhotoFragmentToBtmNavFragment)
        }

        binding?.onePhotoShareIv?.setOnClickListener {
            val sentIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, onePhotoClicked?.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sentIntent, null)
            startActivity(shareIntent)
        }
    }


}