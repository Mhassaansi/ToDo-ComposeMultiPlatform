package com.ubl.todolist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Task?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTask(task: Task)
    @Query("DELETE FROM tasks WHERE id = :taskId")
     fun deleteTask(taskId: Int)
    @Update
     fun updateTask(task: Task)

}