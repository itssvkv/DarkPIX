package com.itssvkv.darkpix.ui.onboardingimport android.content.Contextimport androidx.lifecycle.ViewModelimport com.itssvkv.darkpix.data.local.repository.SharedPrefRepositoryimport dagger.hilt.android.lifecycle.HiltViewModelimport javax.inject.Inject@HiltViewModelclass OnBoardingViewModel @Inject constructor(    private val repo: SharedPrefRepository) : ViewModel() {    fun saveToPref(context: Context, key: String, value: Any) =        repo.saveToPref(context, key, value)}