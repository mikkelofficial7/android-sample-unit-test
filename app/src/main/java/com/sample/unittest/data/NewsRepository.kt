package com.sample.unittest.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.sample.unittest.BuildConfig
import com.sample.unittest.data.local.entity.NewsEntity
import com.sample.unittest.data.local.room.NewsDao
import com.sample.unittest.data.remote.retrofit.ApiService
import com.sample.unittest.utils.wrapEspressoIdlingResource

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.getNews(apiKey = BuildConfig.API_KEY)
                val articles = response.articles
                val newsList = articles.map { article ->
                    NewsEntity(
                        article.title,
                        article.publishedAt,
                        article.urlToImage,
                        article.url
                    )
                }
                emit(Result.Success(newsList))
            } catch (e: Exception) {
                Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }

    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isNewsBookmarked(title)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}