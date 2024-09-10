package com.umitytsr.myapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences{
        return application.getSharedPreferences("my_shared_preferences",Context.MODE_PRIVATE)
    }
}