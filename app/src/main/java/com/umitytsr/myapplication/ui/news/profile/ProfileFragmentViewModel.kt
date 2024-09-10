package com.umitytsr.myapplication.ui.news.profile

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    fun signOut(requireActivity: Activity){
        viewModelScope.launch {
            authenticationRepository.signOut(requireActivity)
        }
    }
}