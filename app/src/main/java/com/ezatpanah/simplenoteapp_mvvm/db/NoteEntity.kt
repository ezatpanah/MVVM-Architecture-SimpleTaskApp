package com.ezatpanah.simplenoteapp_mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezatpanah.simplenoteapp_mvvm.utils.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var cat: String = "",
    var pr: String = "",
)