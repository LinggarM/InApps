package com.incorps.inapps.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CetakDao {
    @Query("SELECT * FROM cart_cetak")
    fun getCetakList(): LiveData<List<Cetak>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCetak(cetak: Cetak)

    @Update
    fun updateCetak(cetak: Cetak)

    @Query("delete from cart_cetak where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun deleteCetak(cetak: Cetak)
}