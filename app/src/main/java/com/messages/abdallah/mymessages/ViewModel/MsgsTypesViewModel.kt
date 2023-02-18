package com.messages.abdallah.mymessages.ViewModel

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messages.abdallah.mymessages.api.ApiService
import com.messages.abdallah.mymessages.models.MsgsModel
import com.messages.abdallah.mymessages.models.MsgsTypesModel
import com.messages.abdallah.mymessages.repository.MsgsRepo
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo
import com.messages.abdallah.mymessages.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MsgsTypesViewModel constructor(
    private val msgsTypesRepo: MsgsTypesRepo,
    private val msgsRepo: MsgsRepo,
    val context: MainActivity

) : ViewModel() {

    private val retrofitService = ApiService.provideRetrofitInstance()

    //     msgsTypesRepo = MsgsTypesRepo(retrofitService)
    val map: HashMap<String, Int> = HashMap()
    private val _response = MutableLiveData<List<MsgsTypesModel>>()
    val responseMsgsTypes: LiveData<List<MsgsTypesModel>>
        get() = _response


    suspend fun getAllMsgsTypes(context: MainActivity): MutableLiveData<List<MsgsTypesModel>> {

        msgsTypesRepo.getMsgsTypes_Ser().let { response ->
            Log.d("sww", "dfrr")
            Log.d("sww", "" + response.body())
            if (response.isSuccessful) {
                // sweilem edit

                Log.i("TestRoom", "getAllMsgsTypes: data returned successful")
                _response.postValue(response.body()?.results)
                Log.i("TestRoom", "getAllMsgsTypes: posts ${response.body()?.results}")
                //here get data from api so will insert it to local database
                msgsTypesRepo.insertPosts(response.body()?.results)
                for (i in response.body()?.results!!) {
                    val list =  MsgsViewModel(msgsRepo).getAllMsgs(i.id)
                    map[i.id.toString()]= list.value!!.size
                }
                this.saveMap(map,context)
                context.hideprogressdialog()

            } else {

                context.hideprogressdialog()

                Log.i("TestRoom", "getAllMsgsTypes: data corrupted")
                Log.d("tag", "getAll Error: ${response.code()}")
            }
        }
        return _response
    }

    fun getPostsFromRoom(context: MainActivity): MutableLiveData<List<MsgsTypesModel>> {
        viewModelScope.launch {
            val response = msgsTypesRepo.getMsgsTypes_Dao()
            withContext(Dispatchers.Main) {
                if (response.isEmpty()) {
                    Log.i("TestRoom", "getPostsFromRoom: will cal api")
                    //no data in database so will call data from API
                    if (internetCheck(context)) {
                        getAllMsgsTypes(context)
                    } else {
                        Toast.makeText(
                            context,
                            "please check your internet connection..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.i("TestRoom", "getPostsFromRoom: get from Room")
                    _response.postValue(response)
                }
            }
        }
        return _response
    }


    fun refreshPosts(context: MainActivity) {

        viewModelScope.launch {
            Log.i("TestRoom", "refreesh")
            if (internetCheck(context)) {
                context.showprogressdialog()
                //  context.hideprogressdialog()
                getAllMsgsTypes(context)
            } else {
                context.hideprogressdialog()

                Toast.makeText(
                    context,
                    "please check your internet connection..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun internetCheck(c: Context): Boolean {
        val cmg = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+
            cmg.getNetworkCapabilities(cmg.activeNetwork)?.let { networkCapabilities ->
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        } else {
            return cmg.activeNetworkInfo?.isConnectedOrConnecting == true
        }

        return false
    }

    private fun saveMap(inputMap: HashMap<String, Int>,context: MainActivity) {
        val pSharedPref: SharedPreferences =
            context.getSharedPreferences("MyVariables", Context.MODE_PRIVATE)
        if (pSharedPref != null) {
            val jsonObject = JSONObject(inputMap as Map<*, *>)
            val jsonString = jsonObject.toString()
            pSharedPref.edit()
                .remove("My_map")
                .putString("My_map", jsonString)
                .apply()
        }
    }
}

