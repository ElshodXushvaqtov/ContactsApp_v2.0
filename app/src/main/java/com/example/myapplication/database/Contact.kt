package com.example.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class Contact(
@PrimaryKey(autoGenerate = true)
var id:Int=0,
var name:String,
var phone:String
)
