package com.nag.todo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nag.todo.model.TodoEntity

@Dao
interface TodoDAO{

    @Query("SELECT * FROM todo_items")
    fun getAll():LiveData<List<TodoEntity>>

    @Insert
    fun insertAll(vararg todoEntity: TodoEntity)

    @Query("SELECT * FROM todo_items WHERE title LIKE:title")
    fun findByTitle(title: String): TodoEntity

    @Delete
    fun delete(todoEntity: TodoEntity)

    @Update
    fun updateTodo(vararg todoEntity: TodoEntity)

}