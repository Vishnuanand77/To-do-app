package com.vishnu.todoapp.data

import androidx.room.TypeConverter
import com.vishnu.todoapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{ //Converts enum to string
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority { //Converts string to enum
        return Priority.valueOf(priority)
    }
}