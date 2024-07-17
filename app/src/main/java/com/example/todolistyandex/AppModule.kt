package com.example.todolistyandex

import android.content.Context
import androidx.room.Room
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import com.example.todolistyandex.data.room.AppDatabase
import com.example.todolistyandex.data.room.ToDoItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "todo-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoItemDao(database: AppDatabase): ToDoItemDao {
        return database.todoItemDao()
    }

    @Provides
    @Singleton
    fun provideToDoItemsRepository(todoItemDao: ToDoItemDao): ToDoItemsRepository {
        return ToDoItemsRepository(todoItemDao)
    }

    @Provides
    @Singleton
    fun provideOnNetworkAvailable(repository: ToDoItemsRepository): suspend () -> Unit {
        return { repository.retryPendingOperations() }
    }

}
