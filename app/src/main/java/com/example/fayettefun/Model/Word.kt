package com.example.fayettefun.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word(
    //Note that we now allow for ID as the primary key
    //It needs to be nullable when creating a new word in the database
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "word") val word: String

)

