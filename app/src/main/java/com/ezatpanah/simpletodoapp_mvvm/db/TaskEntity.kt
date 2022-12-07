package com.ezatpanah.simpletodoapp_mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezatpanah.simpletodoapp_mvvm.utils.Constants.TASK_TABLE

@Entity(tableName = TASK_TABLE)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var cat: String = "",
    var pr: String = "",
)