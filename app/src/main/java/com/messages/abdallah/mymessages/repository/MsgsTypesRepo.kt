package com.messages.abdallah.mymessages.repository

import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.db.LocaleSource
import com.messages.abdallah.mymessages.models.MsgsTypesModel

class MsgsTypesRepo constructor(private val apiService: ApiService,private val localeSource: LocaleSource) {

    suspend fun getMsgsTypes_Ser() = apiService.getMsgsTypes_Ser()

    suspend fun getLocalPosts() = localeSource.getLocalPosts()

    suspend fun insertPosts (posts:List<MsgsTypesModel>?){
        if(!posts.isNullOrEmpty()){
            localeSource.insertPosts(posts)
        }
    }

}