package com.practice.mvvmsolvedapi.api

import com.practice.mvvmsolvedapi.model.SolvedAcGetUserDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface solvedAcAPI {
    @GET("/api/v3/user/show")
    suspend fun getUserData(
        @Query("handle") handle: String
    ): Response<SolvedAcGetUserDataModel>
}