package com.nag.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoEntity(@PrimaryKey(autoGenerate = true)
                        var id: Int = 0,
                        @ColumnInfo(name ="title") var title: String,
                        @ColumnInfo(name ="content") var content: String
)