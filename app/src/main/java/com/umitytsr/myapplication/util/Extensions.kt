package com.umitytsr.myapplication.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDateTime(publishedAt:String): String{
    val zonedDateTime = ZonedDateTime.parse(publishedAt)
    val formatter = DateTimeFormatter.ofPattern("HH.mm/dd.MM.yyyy")
    return zonedDateTime.format(formatter)
}