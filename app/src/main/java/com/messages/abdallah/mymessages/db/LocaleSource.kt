package com.messages.abdallah.mymessages.db

import android.content.Context
import com.messages.abdallah.mymessages.db.Dao.MsgsDao
import com.messages.abdallah.mymessages.db.Dao.MsgsTypesDao
import com.messages.abdallah.mymessages.models.MsgsTypesModel

class LocaleSource(context: Context) {

    private var TypesDao: MsgsTypesDao?
    private var Msgs_Dao: MsgsDao?



    init {
        val dataBase = PostDatabas.getInstance(context.applicationContext)
        TypesDao = dataBase.TypesDao()
        Msgs_Dao = dataBase.Msgs_Dao()
    }

    companion object {
        private var sourceConcreteClass: LocaleSource? = null
        fun getInstance(context: Context): LocaleSource {
            if (sourceConcreteClass == null)
                sourceConcreteClass = LocaleSource(context)
            return sourceConcreteClass as LocaleSource
        }
    }

    suspend fun getLocalPosts(): List<MsgsTypesModel> {
        return TypesDao?.getLocalPosts()!!
    }

    suspend fun insertPosts(posts: List<MsgsTypesModel>) {
        TypesDao?.insertPosts(posts)!!
    }

    suspend fun deletePosts() {
        TypesDao?.deleteALlPosts()
    }

}