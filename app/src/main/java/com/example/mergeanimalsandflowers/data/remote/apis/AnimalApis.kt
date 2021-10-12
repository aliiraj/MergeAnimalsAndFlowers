package com.example.mergeanimalsandflowers.data.remote.apis

import com.example.mergeanimalsandflowers.data.remote.dto.MainAnimalResponseDto
import retrofit2.http.*


interface AnimalApis {

    @GET("intern.android/?kind=animal")
    suspend fun getAnimals(): MainAnimalResponseDto

}