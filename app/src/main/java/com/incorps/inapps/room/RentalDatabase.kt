package com.incorps.inapps.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Rental::class], version = 1, exportSchema = false)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun rentalDao(): RentalDao

    companion object {
        @Volatile
        private var INSTANCE: RentalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RentalDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, RentalDatabase::class.java,
                    "cart_rental").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}