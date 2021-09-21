package com.example.mergeanimalsandflowers.data.apis

import com.example.mergeanimalsandflowers.data.models.MainAnimalResponseModel
import retrofit2.http.*


interface AnimalApis {

    @GET("intern.android/?kind=animal")
    suspend fun getAnimals(): MainAnimalResponseModel

}