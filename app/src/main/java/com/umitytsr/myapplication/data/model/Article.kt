package com.umitytsr.myapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "favorite")
@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: @RawValue Source? = null,
    val title: String?,
    val url: String?,
    @PrimaryKey
    val urlToImage: String
):Parcelable{
    override fun hashCode(): Int {
        var result = urlToImage.hashCode()
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (publishedAt?.hashCode() ?: 0)
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Article) return false

        return urlToImage == other.urlToImage &&
                author == other.author &&
                content == other.content &&
                description == other.description &&
                publishedAt == other.publishedAt &&
                source == other.source &&
                title == other.title &&
                url == other.url
    }
}