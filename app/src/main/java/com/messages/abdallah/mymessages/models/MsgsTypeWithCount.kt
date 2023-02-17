package com.messages.abdallah.mymessages.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class MsgsTypeWithCount(
    @Embedded
    var msgsTypesModel: MsgsTypesModel?=null,

    @ColumnInfo(name = "counter")
    var counter: Int
)
