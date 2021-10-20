package com.example.guesstheword.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(@PrimaryKey(autoGenerate = true) var scoreID: Long,@ColumnInfo var score:Int, @ColumnInfo(name = "lastUpdate") val lastUpdate: String?)