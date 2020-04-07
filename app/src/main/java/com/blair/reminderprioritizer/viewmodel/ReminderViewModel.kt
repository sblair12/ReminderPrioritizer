package com.blair.reminderprioritizer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blair.reminderprioritizer.db.ReminderRoomDatabase
import com.blair.reminderprioritizer.entity.Reminder
import com.blair.reminderprioritizer.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReminderRepository
    val allReminders: LiveData<List<Reminder>>

    init {
        val reminderDao = ReminderRoomDatabase.getDatabase(application, viewModelScope).reminderDao()
        repository = ReminderRepository(reminderDao)
        allReminders = repository.allReminders
    }

    fun add(reminder: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.add(reminder)
    }
}