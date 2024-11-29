package com.sample.unittest.utils

import com.sample.unittest.data.local.entity.NewsEntity

object DataDummy {

    fun generateDummyNewsEntity(): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        for (i in 0..10) {
            val news = NewsEntity(
                "Title $i",
                "2022-02-22T22:22:22Z",
                "https://google-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "https://www.google.com/",
            )
            newsList.add(news)
        }
        return newsList
    }
}