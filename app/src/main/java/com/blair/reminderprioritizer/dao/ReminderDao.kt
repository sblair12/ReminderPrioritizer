package com.blair.reminderprioritizer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blair.reminderprioritizer.entity.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAll(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE id=:id")
    fun getById(id: Int): Reminder

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(reminder: Reminder)

    @Query("DELETE FROM reminder WHERE id=:id")
    suspend fun deleteById(id: Int)
}