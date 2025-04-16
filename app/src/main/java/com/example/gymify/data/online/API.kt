package com.example.gymify.data.online

import com.example.gymify.domain.models.ExcersiceItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface API {


    @Headers(
        "x-rapidapi-key: 75884b0375mshbafa139d6fc7140p1b4e35jsn310cc6ee2db3",
        "x-rapidapi-host: exercisedb.p.rapidapi.com"
    )
    @GET("exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<List<ExcersiceItem>>


    @Headers(
        "x-rapidapi-key: 75884b0375mshbafa139d6fc7140p1b4e35jsn310cc6ee2db3",
        "x-rapidapi-host: exercisedb.p.rapidapi.com"
    )
    @GET("exercises/exercise/{id}")
    suspend fun getExerciseById(
        @Path("id") id: String
    ): Response<ExcersiceItem>



}