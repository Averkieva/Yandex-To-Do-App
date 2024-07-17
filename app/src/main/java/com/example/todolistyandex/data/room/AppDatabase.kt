package com.example.todolistyandex.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolistyandex.data.model.ToDoItem

@Database(entities = [ToDoItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): ToDoItemDao
}