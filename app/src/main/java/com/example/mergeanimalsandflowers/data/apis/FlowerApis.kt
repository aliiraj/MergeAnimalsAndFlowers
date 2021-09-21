package com.example.mergeanimalsandflowers.data.apis

import com.example.mergeanimalsandflowers.data.models.MainFlowerResponseModel
import retrofit2.http.*


interface FlowerApis {

    @GET("intern.android/?kind=flower")
    suspend fun getFlowers(): MainFlowerResponseModel

}