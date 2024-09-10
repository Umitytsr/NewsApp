package com.umitytsr.myapplication.ui.authentication.signInScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umitytsr.myapplication.data.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {


    fun signIn(email: String,password: String){
        viewModelScope.launch {
            authenticationRepository.signIn(email,password)
        }
    }
}