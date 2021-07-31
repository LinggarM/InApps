package com.incorps.inapps.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DesainDao {
    @Query("SELECT * FROM cart_desain")
    fun getDesainList(): LiveData<List<Desain>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDesain(desain: Desain)

    @Update
    fun updateDesain(desain: Desain)

    @Query("delete from cart_desain where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun deleteDesain(desain: Desain)
}