package com.sample.unittest.di

import android.content.Context
import com.sample.unittest.data.NewsRepository
import com.sample.unittest.data.local.room.NewsDatabase
import com.sample.unittest.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}