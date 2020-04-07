package com.blair.reminderprioritizer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blair.reminderprioritizer.dao.ReminderDao
import com.blair.reminderprioritizer.entity.Reminder
import kotlinx.coroutines.CoroutineScope

// exportSchema corresponds to Database migrations, might need a fix later
@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderRoomDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        // Singleton design

        @Volatile
        private var INSTANCE: ReminderRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): ReminderRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderRoomDatabase::class.java,
                    "reminder-database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}