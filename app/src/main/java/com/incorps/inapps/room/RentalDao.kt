package com.incorps.inapps.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RentalDao {
    @Query("SELECT * FROM cart_rental")
    fun getRentalList(): LiveData<List<Rental>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRental(rental: Rental)

    @Update
    fun updateRental(rental: Rental)

    @Query("delete from cart_rental where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun deleteRental(rental: Rental)
}