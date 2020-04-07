package com.blair.reminderprioritizer.repository

import androidx.lifecycle.LiveData
import com.blair.reminderprioritizer.dao.ReminderDao
import com.blair.reminderprioritizer.entity.Reminder

class ReminderRepository(private val reminderDao: ReminderDao) {
    val allReminders: LiveData<List<Reminder>> = reminderDao.getAll()

    suspend fun add(reminder: Reminder) {
        reminderDao.add(reminder)
    }
}