package com.blair.reminderprioritizer.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "description") val description: String?
)