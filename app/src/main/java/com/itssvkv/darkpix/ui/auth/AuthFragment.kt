package com.itssvkv.darkpix.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itssvkv.darkpix.R
import com.itssvkv.darkpix.databinding.FragmentAuthBinding
import com.itssvkv.darkpix.ui.MainActivity
import com.itssvkv.darkpix.utils.Common
import com.itssvkv.darkpix.utils.Common.SIGN_IN_URI
import com.itssvkv.darkpix.utils.safeapi.CallState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var binding: FragmentAuthBinding? = null

    @Inject
    lateinit var intent: Intent
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        initSignInBtnClick()
        return binding?.root
    }


    private fun initSignInBtnClick() {
        binding?.authSignInBtn?.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(SIGN_IN_URI))
            startActivity(intent)

        }
    }


    override fun onResume() {
        super.onResume()
        if ((activity as MainActivity).code.isNotEmpty()) {
            getAccessToken()
            collectState()
        }
    }

    private fun getAccessToken() {
        lifecycleScope.launch {
            viewModel.getAccessToken(
                Common.CLIENT_ID,
                Common.CLIENT_SECRET,
                Common.REDIRECT_URI,
                (activity as MainActivity).code,
                Common.GRANT_TYPE,
                requireContext()
            )
        }

    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.accessTokenResponse.collectLatest {
                when (it) {
                    is CallState.SuccessState<*> -> {
                        this@AuthFragment.findNavController()
                            .navigate(R.id.authFragmentToBottomNav)
                    }

                    is CallState.FailureState -> Log.d(Common.SAKKA, "collectState:${it.msg} ")
                    else -> {}
                }
            }
        }
    }


}