package com.messages.abdallah.mymessages.models

import androidx.room.Embedded

data class MsgsTypeWithCount(
    @Embedded
    var msgsTypesModel: MsgsTypesModel,

    var counter: Int
)
