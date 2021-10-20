package com.example.guesstheword.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(@PrimaryKey(autoGenerate = true) var P_ID: Int=0, @ColumnInfo var P_Name: String="", @ColumnInfo var P_Email: String="",@ColumnInfo var P_Username: String="",@ColumnInfo var P_Password: String="")