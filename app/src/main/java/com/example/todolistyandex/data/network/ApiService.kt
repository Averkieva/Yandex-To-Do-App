package com.example.todolistyandex.data.network

import com.example.todolistyandex.data.model.RemoteTodoItem
import com.example.todolistyandex.data.network.request.TodoItemRequest
import com.example.todolistyandex.data.network.response.TodoItemResponse
import com.example.todolistyandex.data.network.response.TodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interface representing the API service for managing ToDoItems, providing methods
 * to retrieve, add, update, and delete tasks via HTTP requests.
 */

interface ApiService {
    @GET("list")
    suspend fun getTodoList(): Response<TodoListResponse>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItem: TodoItemRequest
    ): Response<TodoItemResponse>

    @PUT("list/{id}")
    suspend fun updateTodoItem(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItem: TodoItemRequest
    ): Response<TodoItemResponse>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Path("id") id: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<TodoItemResponse>

    @PATCH("list")
    suspend fun updateTodoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoList: List<RemoteTodoItem>
    ): Response<TodoListResponse>

}