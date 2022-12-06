package com.ezatpanah.simplenoteapp_mvvm.repository


import com.ezatpanah.simplenoteapp_mvvm.db.NoteDao
import com.ezatpanah.simplenoteapp_mvvm.db.NoteEntity
import javax.inject.Inject

class DbRepository  @Inject constructor(private val dao : NoteDao){

    suspend fun saveNote (entity: NoteEntity) = dao .saveNote(entity)
    suspend fun updateNote(entity: NoteEntity)= dao.updateNote(entity)
    suspend fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)

    fun getAllNotes() = dao.getAllNotes()
    fun getDetailsNote(id:Int)= dao.getNote(id)
    fun filterNote(priority: String) = dao.filterNote(priority)
    fun searchNote(title: String) = dao.searchNote(title)
}