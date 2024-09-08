package com.umitytsr.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.util.DatabaseConverter

@Database(entities = [Article::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class NewsDb: RoomDatabase() {
    abstract fun newsPropertyDao() : NewsDao
}