package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): DAO

    companion object {

        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (instance == null) {

                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .allowMainThreadQueries()
                    .build()

            }


            return instance!!
        }

    }

}