package com.ezatpanah.simpletodoapp_mvvm.db

import androidx.room.*
import com.ezatpanah.simpletodoapp_mvvm.utils.Constants.TASK_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(entity: TaskEntity)

    @Update
    suspend fun updateTask(entity: TaskEntity)

    @Delete
    suspend fun deleteTask(entity: TaskEntity)

    @Query("SELECT * FROM $TASK_TABLE")
    fun getAllTasks(): Flow<MutableList<TaskEntity>>

    @Query("DELETE FROM $TASK_TABLE")
    fun deleteAllTask()


    @Query("SELECT * FROM $TASK_TABLE WHERE id ==:id")
    fun getTask(id: Int): Flow<TaskEntity>

    @Query("SELECT * FROM $TASK_TABLE WHERE pr == :priority")
    fun filterTask(priority: String): Flow<MutableList<TaskEntity>>

    @Query("SELECT * FROM $TASK_TABLE WHERE title LIKE '%' || :title || '%' ")
    fun searchTask(title: String): Flow<MutableList<TaskEntity>>

}