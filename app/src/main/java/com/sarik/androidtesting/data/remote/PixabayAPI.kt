package com.androiddevs.shoppinglisttestingyt.data.remote

import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.sarik.androidtesting.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.image_api_key
    ): Response<ImageResponse>
}