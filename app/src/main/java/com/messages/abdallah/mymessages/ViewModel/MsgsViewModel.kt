package com.messages.abdallah.mymessages.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.models.MsgsModel
import com.messages.abdallah.mymessages.repository.MsgsRepo
import kotlinx.coroutines.launch

class MsgsViewModel:ViewModel() {

    private val retrofitService = ApiService.provideRetrofitInstance()
    private val msgsRepo = MsgsRepo(retrofitService)
    private val _response = MutableLiveData<List<MsgsModel>>()

    suspend fun getAllMsgs(ID_Type_id:Int) :MutableLiveData<List<MsgsModel>> {
        //var ID_Type_id:Int ?=null
//        if (ID_Type_id != null) {
        msgsRepo.getMsgs_Ser(ID_Type_id).let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body()?.results)
                Log.i("TestRoom", "getAllMsgs: posts ${response.body()?.results}")
                // msgsTypesRepo.insertPosts(response.body()?.results)
            } else {
                Log.i("TestRoom", "getAllMsgs: data corrupted")
                Log.d("tag", "getAll Error: ${response.code()}")
                Log.d("tag", "getAll: ${response.body()}")
            }
        }
        return _response
    }
}
//}




