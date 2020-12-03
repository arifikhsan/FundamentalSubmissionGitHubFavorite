package com.arifikhsan.githubfavorite.repository

import com.arifikhsan.githubfavorite.entity.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

class GitHubRepository {
    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(GitHub::class.java)
}

interface GitHub {
    @Headers("Authorization: df97872248fb3eecacba97569ad7156b9674c9df")
    @GET("users/{username}")
    fun getDetailUserByUsername(@Path("username") username: String): Call<User>
}