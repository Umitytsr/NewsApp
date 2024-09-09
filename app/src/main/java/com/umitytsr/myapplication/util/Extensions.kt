package com.umitytsr.myapplication.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDateTime(publishedAt:String): String{
    val zonedDateTime = ZonedDateTime.parse(publishedAt)
    val formatter = DateTimeFormatter.ofPattern("HH.mm/dd.MM.yyyy")
    return zonedDateTime.format(formatter)
}
/*
fun initDescriptionNewsRecyclerView(
    fragment: Fragment,
    newsList: List<Article>,
    recyclerView: RecyclerView
) {
    val adapter = NewsDescriptionAdapter(newsList, fragment)
    val layoutManager = LinearLayoutManager(fragment.requireContext(), RecyclerView.VERTICAL, false)
    with(recyclerView) {
        this.adapter = adapter
        this.layoutManager = layoutManager
        setHasFixedSize(true)
    }
}

 */