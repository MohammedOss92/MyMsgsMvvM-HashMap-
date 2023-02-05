package com.messages.abdallah.mymessages.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.messages.abdallah.mymessages.repository.MsgsTypesRepo

//class MyViewModelFactory constructor(private val repository: MsgsTypesRepo): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(MsgsTypesViewModel::class.java)) {
//            MsgsTypesViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}