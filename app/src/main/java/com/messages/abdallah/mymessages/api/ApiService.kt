package com.messages.abdallah.mymessages.api

import com.messages.abdallah.mymessages.models.MsgsTypesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("msgtypes_api")
    //sweilem edit
    suspend fun getMsgsTypes_Ser(): Response<MsgsTypesResponse>



    companion object {
        var retrofitService: ApiService? = null
        fun provideRetrofitInstance(): ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://abdallah92.pythonanywhere.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
        return retrofitService!!
    }

    }

    }

