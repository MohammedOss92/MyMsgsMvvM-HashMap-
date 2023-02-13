package com.messages.abdallah.mymessages.models

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class MsgsModel(

    @SerializedName("id")
    @NonNull
    var id : Int = 0,

    @SerializedName("MessageName")
    var MessageName : String,

    @SerializedName("new_msgs")
    var new_msgs : Int,

    @SerializedName("ID_Type_id")
    var ID_Type_id : Int
)