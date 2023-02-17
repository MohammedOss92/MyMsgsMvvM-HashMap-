package com.messages.abdallah.mymessages.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.messages.abdallah.mymessages.models.MsgsTypeWithCount
import com.messages.abdallah.mymessages.models.MsgsTypesModel

@Dao
interface MsgsTypesDao {

    @Query("SELECT * FROM msg_types_table")
    fun getPosts(): LiveData<List<MsgsTypesModel>>

    @Query("SELECT * FROM msg_types_table")
    fun getPosts2():  List<MsgsTypesModel>

    // get last id to compare with server data
    @Query("SELECT * FROM msg_types_table ORDER BY id DESC LIMIT 1")
    fun getLastId(): MsgsTypesModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(msgsTypesModels: MsgsTypesModel)

    @Update
    suspend fun update(note: MsgsTypesModel)

    @Delete
    suspend fun delete(note: MsgsTypesModel)

    /************************/

//    @Query("SELECT * FROM msg_types_table")
//    suspend fun getMsgsTypes_Dao():  List<MsgsTypesModel>

    @Query("SELECT e.counter AS counter, c.* FROM msg_types_table c \n" +
            "LEFT JOIN ( \n" +
            "   SELECT ID_Type_id, count(*) counter\n" +
            "   from msg_table e  \n" +
            "   GROUP BY ID_Type_id) e\n" +
            "ON e.ID_Type_id = c.id")
    suspend fun getMsgsTypes_Dao():  List<MsgsTypeWithCount>





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<MsgsTypesModel>)

    @Query("DELETE FROM msg_types_table")
    fun deleteALlPosts()
}