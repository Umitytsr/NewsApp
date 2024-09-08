package com.umitytsr.myapplication.di

import android.content.Context
import androidx.room.Room
import com.umitytsr.myapplication.data.db.NewsDao
import com.umitytsr.myapplication.data.db.NewsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNewsDb(@ApplicationContext context: Context): NewsDb{
        return Room.databaseBuilder(
            context,
            NewsDb::class.java,
            "favorite"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDb: NewsDb): NewsDao{
        return newsDb.newsPropertyDao()
    }
}