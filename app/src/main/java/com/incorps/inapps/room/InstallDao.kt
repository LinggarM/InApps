package com.incorps.inapps.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InstallDao {
    @Query("SELECT * FROM cart_install")
    fun getInstallList(): LiveData<List<Install>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInstall(install: Install)

    @Update
    fun updateInstall(install: Install)

    @Query("delete from cart_install where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun deleteInstall(install: Install)
}