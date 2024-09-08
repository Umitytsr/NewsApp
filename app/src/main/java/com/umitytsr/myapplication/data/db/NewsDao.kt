package com.umitytsr.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umitytsr.myapplication.data.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsFavoriteProperties(newsFavorite: Article)

    @Query("SELECT * FROM favorite")
    suspend fun getAllNewsFavorite(): List<Article>

    @Delete
    suspend fun deleteNewsFavorite(newsFavorite: Article)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id= :id)")
    suspend fun isFavorite(id:Int): Boolean
}