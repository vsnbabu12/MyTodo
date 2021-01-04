package com.nag.todo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nag.todo.model.TodoEntity
import com.nag.todo.repositories.TodoRepositories

class TodoViewModel(app: Application) : AndroidViewModel(app){

    private val repository = TodoRepositories(app)
    private val todos = repository.getAllTodos()

    fun insert(todoEntity: TodoEntity) {
        repository.insert(todoEntity)
    }

    fun update(todoEntity: TodoEntity) {
        repository.update(todoEntity)
    }

    fun delete(todoEntity: TodoEntity) {
        repository.delete(todoEntity)
    }

    fun getAllTodos(): LiveData<List<TodoEntity>> {
        return todos
    }

}