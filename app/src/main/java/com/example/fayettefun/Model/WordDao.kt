package com.example.fayettefun.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fayettefun.Model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    //Get all words alphabetized
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    //Get a single word with a given id
    @Query("SELECT * FROM word_table WHERE id=:id")
    fun getWord(id:Int): Flow<Word>

    //Insert a single word
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    //Delete all words
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    //Update a single word
    @Update
    suspend fun update(word: Word):Int
}
