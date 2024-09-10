package com.umitytsr.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String?,
    val name: String?
):Parcelable{
    override fun hashCode(): Int {
        return id?.hashCode() ?: name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Source) return false

        return id == other.id && name == other.name
    }
}