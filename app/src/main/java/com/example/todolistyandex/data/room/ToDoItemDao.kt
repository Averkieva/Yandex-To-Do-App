package com.example.todolistyandex.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolistyandex.data.model.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {
    @Query("SELECT * FROM todo_items")
    fun getTodoItems(): Flow<List<ToDoItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateTodoItem(todoItem: ToDoItemEntity)

    @Delete
    suspend fun deleteTodoItem(todoItem: ToDoItemEntity)

    @Query("DELETE FROM todo_items")
    suspend fun clearAll()

}