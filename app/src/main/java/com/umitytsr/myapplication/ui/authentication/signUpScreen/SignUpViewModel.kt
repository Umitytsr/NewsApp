package com.umitytsr.myapplication.ui.authentication.signUpScreen

import androidx.lifecycle.ViewModel
import com.umitytsr.myapplication.data.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel(){

    fun signUp(email: String, password: String, confirmPassword: String){
        authenticationRepository.signUp(email,password,confirmPassword)
    }
}