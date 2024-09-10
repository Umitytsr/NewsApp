package com.umitytsr.myapplication.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.umitytsr.myapplication.data.model.Article
import com.umitytsr.myapplication.ui.news.home.NewsDescriptionAdapter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDateTime(publishedAt: String): String {
    val zonedDateTime = ZonedDateTime.parse(publishedAt)
    val formatter = DateTimeFormatter.ofPattern("HH.mm/dd.MM.yyyy")
    return zonedDateTime.format(formatter)
}

fun Fragment.getDescriptionNewsAdapter(
    news: List<Article>,
    action: (Article) -> Unit
): NewsDescriptionAdapter {
    return NewsDescriptionAdapter(news) { position ->
        val newsUI = news[position]
        action(newsUI)
    }
}

fun applyTheme(isDarkModeEnabled: Boolean) {
    val mode = if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    AppCompatDelegate.setDefaultNightMode(mode)
}