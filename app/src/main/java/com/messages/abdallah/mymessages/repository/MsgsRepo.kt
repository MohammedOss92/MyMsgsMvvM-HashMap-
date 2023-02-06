package com.messages.abdallah.mymessages.repository

import com.messages.abdallah.mymessages.api.ApiService

class MsgsRepo constructor(val apiService: ApiService) {

    suspend fun getMsgs_Ser(ID_Type_id:Int)= apiService.getMsgs_Ser(ID_Type_id)
}