package com.incorps.inapps.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Rental::class, Desain::class, Cetak::class, Install::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun rentalDao(): RentalDao
    abstract fun desainDao(): DesainDao
    abstract fun cetakDao(): CetakDao
    abstract fun installDao(): InstallDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CartDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, CartDatabase::class.java,
                    "cart").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}