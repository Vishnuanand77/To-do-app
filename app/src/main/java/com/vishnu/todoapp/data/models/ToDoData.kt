package com.vishnu.todoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData (
    @PrimaryKey(autoGenerate = true) //Used to autogenerate IDs for the table
    var id: Int, //Primary key
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable