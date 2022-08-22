package com.practice.mvvmsolvedapi.api

import com.practice.mvvmsolvedapi.common.GlobalApplication

class solvedAcAPIRepository {
    private val solvedAcClient = GlobalApplication.baseService.create(solvedAcAPI::class.java)

    suspend fun getUserData(handle: String) = solvedAcClient.getUserData(handle)
}