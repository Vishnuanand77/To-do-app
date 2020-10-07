package com.vishnu.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoData (
    @PrimaryKey(autoGenerate = true) //Used to autogenerate IDs for the table
    var id: Int, //Primary key
    var title: String,
    var priority: Priority,
    var description: String
)