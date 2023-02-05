package com.messages.abdallah.mymessages.repository

import com.messages.abdallah.mymessages.api.ApiService

class MsgsTypesRepo constructor(private val apiService: ApiService) {

    suspend fun getMsgsTypes_Ser() = apiService.getMsgsTypes_Ser()

}