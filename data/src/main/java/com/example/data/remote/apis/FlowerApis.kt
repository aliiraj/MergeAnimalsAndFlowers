package com.example.mergeanimalsandflowers.data.remote.apis

import com.example.mergeanimalsandflowers.data.remote.dto.MainFlowerResponseDto
import retrofit2.http.*


interface FlowerApis {

    @GET("intern.android/?kind=flower")
    suspend fun getFlowers(): MainFlowerResponseDto

}