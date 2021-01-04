package com.nag.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nag.todo.model.TodoEntity
import com.nag.todo.utils.subscribeOnBackground

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDao(): TodoDAO

    companion object{
        private var instance: TodoDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TodoDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, TodoDatabase::class.java,
                            "todo_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: TodoDatabase) {
            val todoDAO = db.todoDao()
            subscribeOnBackground {
                todoDAO.insertAll(TodoEntity(1,"nagendra","birthday"))
            }

        }
    }

}