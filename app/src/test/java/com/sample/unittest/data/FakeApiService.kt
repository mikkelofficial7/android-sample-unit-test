package com.sample.unittest.data

import com.sample.unittest.data.remote.response.NewsResponse
import com.sample.unittest.data.remote.retrofit.ApiService
import com.sample.unittest.utils.DataDummy

class FakeApiService : ApiService {
    private val dummyResponse = DataDummy.generateDummyNewsResponse()

    override suspend fun getNews(country: String, apiKey: String): NewsResponse {
        return dummyResponse
    }
}