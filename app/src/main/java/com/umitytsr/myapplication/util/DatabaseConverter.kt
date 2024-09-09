package com.umitytsr.myapplication.util

import androidx.room.TypeConverter
import com.umitytsr.myapplication.data.model.Source

class DatabaseConverter {
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name,name)
    }

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }
}