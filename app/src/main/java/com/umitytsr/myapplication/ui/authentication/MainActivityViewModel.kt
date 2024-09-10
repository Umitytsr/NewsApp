package com.umitytsr.myapplication.ui.authentication

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) :ViewModel(){

    private val _isDarkModeResult = MutableStateFlow<Boolean>(false)
    val isDarkModeResult : StateFlow<Boolean> = _isDarkModeResult.asStateFlow()

    init {
        darkModePreference()
    }

    fun setDarkModeEnabled(isChecked: Boolean){
        viewModelScope.launch {
            _isDarkModeResult.emit(isChecked)
            sharedPreferences.edit().putBoolean("darkModeEnabled",isChecked).apply()
        }
    }

    private fun darkModePreference(){
        val isDarkModeEnabled = sharedPreferences.getBoolean("darkModeEnabled",false)
        viewModelScope.launch {
            _isDarkModeResult.emit(isDarkModeEnabled)
        }
    }
}