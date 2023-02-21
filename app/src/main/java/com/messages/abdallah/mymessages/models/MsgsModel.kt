package com.messages.abdallah.mymessages.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "msg_table",
    foreignKeys =[ForeignKey(entity = MsgsTypesModel::class, childColumns = ["TypeDescription"], parentColumns = ["TypeID"])])
data class MsgsModel(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "Msg_ID")
    @NonNull
    var id : Int = 0,

    @ColumnInfo("MsgDescription")
    @SerializedName("MessageName")
    var MessageName : String,

    @ColumnInfo("new_msgs")
    @SerializedName("new_msgs")
    var new_msgs : Int,

    @ColumnInfo("TypeDescription", index = true)
    @SerializedName("ID_Type_id")
    var ID_Type_id : String
)