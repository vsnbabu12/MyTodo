package com.nag.todo.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.nag.todo.db.TodoDAO
import com.nag.todo.db.TodoDatabase
import com.nag.todo.model.TodoEntity
import com.nag.todo.utils.subscribeOnBackground

class TodoRepositories(application: Application) {

    private var todoDao: TodoDAO
    private var todos: LiveData<List<TodoEntity>>

    private val database = TodoDatabase.getInstance(application)

    init {
        todoDao = database.todoDao()
        todos = todoDao.getAll()
    }

    fun insert(todoEntity: TodoEntity) {
        subscribeOnBackground{
            todoDao.insertAll(todoEntity)
        }

    }

    fun update(todoEntity: TodoEntity) {
        subscribeOnBackground {
            todoDao.updateTodo(todoEntity)
        }
    }

    fun delete(todoEntity: TodoEntity) {
        subscribeOnBackground {
            todoDao.delete(todoEntity)
        }
    }

    fun getAllTodos(): LiveData<List<TodoEntity>> {
        return todos
    }



}