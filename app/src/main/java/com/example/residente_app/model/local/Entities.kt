package com.example.residente_app.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class User(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name:String = "",
    val lastName:String = "",
    val username:String = "",
    val password: String = "",
    val role:String = "user",
)
