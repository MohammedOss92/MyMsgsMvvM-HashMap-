package com.messages.abdallah.mymessages.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.models.MsgsModel
import com.messages.abdallah.mymessages.repository.MsgsRepo
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MsgsViewModel constructor(private val msgsRepo:MsgsRepo):ViewModel() {

    private val retrofitService = ApiService.provideRetrofitInstance()

    private val _response = MutableLiveData<List<MsgsModel>>()

    suspend fun getAllMsgs(ID_Type_id:Int) :MutableLiveData<List<MsgsModel>> {

        msgsRepo.getMsgs_Ser(ID_Type_id).let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body()?.results)
                Log.i("TestRoom", "getAllMsgs: posts ${response.body()?.results}")
                msgsRepo.insert_msgs(response.body()?.results)
            } else {
                Log.i("TestRoom", "getAllMsgs: data corrupted")
                Log.d("tag", "getAll Error: ${response.code()}")
                Log.d("tag", "getAll: ${response.body()}")
            }
        }
        return _response
    }

    fun getMsgsFromRoom(ID_Type_id:Int){
        viewModelScope.launch {
            val response = msgsRepo.getMsgs_Dao()
            withContext(Dispatchers.Main) {
                if (response.isEmpty()) {
                    Log.i("TestRoom", "getPostsFromRoom: will cal api")
                    //no data in database so will call data from API
                    getAllMsgs(ID_Type_id)
                } else {
                    Log.i("TestRoom", "getPostsFromRoom: get from Room")
                    _response.postValue(response)
                }
            }
        }
    }

    suspend fun refreshMsgs(ID_Type_id:Int) {
        getAllMsgs(ID_Type_id)
    }
}
//}




