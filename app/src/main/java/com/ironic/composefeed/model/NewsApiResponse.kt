package com.rounik.milab.newsfeed.model

import com.ironic.composefeed.model.News

data class NewsApiResponse(
    val status: String,
    val articles: List<News>
)