package com.messages.abdallah.mymessages.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "msg_table",
    foreignKeys =[ForeignKey(entity = MsgsTypesModel::class, childColumns = ["ID_Type_id"], parentColumns = ["id"])])
data class MsgsModel(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @NonNull
    var id : Int = 0,

    @ColumnInfo("MessageName")
    @SerializedName("MessageName")
    var MessageName : String,

    @ColumnInfo("new_msgs")
    @SerializedName("new_msgs")
    var new_msgs : Int,

    @ColumnInfo("ID_Type_id")
    @SerializedName("ID_Type_id")
    var ID_Type_id : Int
)