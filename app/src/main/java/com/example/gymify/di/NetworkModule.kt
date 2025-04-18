package com.example.gymify.di

import com.example.gymify.data.online.API
import com.example.gymify.data.online.ChatGptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChatGptRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExerciseRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ExerciseRetrofit // Apply the qualifier here
    fun provideExerciseRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ExerciseRetrofit // Apply the qualifier here
    fun provideExerciseApiService(@ExerciseRetrofit retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "75884b0375mshbafa139d6fc7140p1b4e35jsn310cc6ee2db3")
                .addHeader("X-RapidAPI-Host", "chatgpt-42.p.rapidapi.com")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
            .build()

    }

    @Provides
    @Singleton
    @ChatGptRetrofit // Apply the qualifier here
    fun provideChatGptRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://chatgpt-42.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @ChatGptRetrofit // Apply the qualifier here
    fun provideChatGptApi(@ChatGptRetrofit retrofit: Retrofit): ChatGptApi {
        return retrofit.create(ChatGptApi::class.java)
    }
}
