package com.itssvkv.darkpix.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.itssvkv.darkpix.databinding.ActivityMainBinding
import com.itssvkv.darkpix.models.responses.PixelsPhotosResponse
import com.itssvkv.darkpix.utils.Common.SAKKA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var code: String = ""
    var onePhotoClicked: PixelsPhotosResponse.Photo? = null
    var searchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    private fun getAccessToken() {
        val uri: Uri? = intent?.data
        if (uri != null) {
            code = uri.toString().split("=")[1]
            Log.d(SAKKA, "onResume: $code")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(SAKKA, "onDestroy: ")
    }


}