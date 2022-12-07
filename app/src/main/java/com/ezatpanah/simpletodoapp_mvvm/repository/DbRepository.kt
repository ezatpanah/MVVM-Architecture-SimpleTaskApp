package com.ezatpanah.simpletodoapp_mvvm.repository


import com.ezatpanah.simpletodoapp_mvvm.db.TaskDao
import com.ezatpanah.simpletodoapp_mvvm.db.TaskEntity
import javax.inject.Inject

class DbRepository  @Inject constructor(private val dao : TaskDao){

    suspend fun saveTask (entity: TaskEntity) = dao .saveTask(entity)
    suspend fun updateTask(entity: TaskEntity)= dao.updateTask(entity)
    suspend fun deleteTask(entity: TaskEntity) = dao.deleteTask(entity)

    fun getAllTasks() = dao.getAllTasks()
    fun deleteAllTasks() = dao.deleteAllTask()

    fun getDetailsTask(id:Int)= dao.getTask(id)

    fun filterTask(priority: String) = dao.filterTask(priority)
    fun searchTask(title: String) = dao.searchTask(title)
}